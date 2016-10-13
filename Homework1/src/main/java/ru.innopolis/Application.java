package ru.innopolis;

import ru.innopolis.resource.Summator;
import ru.innopolis.threading.ResourceReader;
import ru.innopolis.threading.ThreadController;
import ru.innopolis.threading.TotalWriter;
import ru.innopolis.utils.Validator;

import java.util.*;

/**
 * Created by Smith on 12.10.2016.
 */
public class Application {
    Set<Thread> res_threads = new HashSet<>();  // there are threads that created by ResourceReader
    Set<Thread> threads = new HashSet<>();      // there are all threads
    List<String> files = new ArrayList<>();     // a list of filenames, given from args or manually asked from user
    Validator validator = new Validator() {     // validator implementation. Only even positive number is valid.
        @Override
        public boolean isValid(Integer i) {
            return (i%2==0) && (i>0);
        }
    };

    public static void main(String[] args) {
        Application app = new Application(); // init app
        ThreadController controller = new ThreadController();
        Summator summator = Summator.getInstance(); // expected that getInstance create new instance.
        if(args.length > 0){
            app.getFilenamesFromArgs(args);
        }else {
            app.getFilenamesFromUser();
        }
        for(String filename : app.files){
            System.out.println(filename);   // TODO to logging
        }

        for(String filename : app.files){
            app.res_threads.add(new Thread(new ResourceReader(filename, app.validator, controller)));
        }

        app.threads.addAll(app.res_threads);
        app.threads.add(new Thread(new TotalWriter()));

        for(Thread thread : app.threads){
            thread.start();
        }

        while(!app.isResourcesThreadsFinished()){
            synchronized (controller) {
                try {
                    controller.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(summator);
            }
        }
        for (Thread thread : app.threads){
            thread.interrupt();
        }
        System.out.println("BUY"); // TODO: 13.10.2016 log it
    }

    /**
     * There is method for getting filenames from parameters.
     * Please override the menthod, if you want to concrete type
     * or fo something else.
     * @param args - array of strings, that given from console
     *             command after executable filename.
     */
    private void getFilenamesFromArgs(String[] args) {
        for(String arg : args){
            this.files.add(arg.replace("\\","\\\\"));
        }
    }

    /**
     * The method for getting filenames by dialog with user.
     */
    private void getFilenamesFromUser(){
        System.out.println("Please, print all files, that you want to be processed:(separate by space and confirm by enter)");
        Scanner scanner = new Scanner(System.in);
        String[] args = scanner.next().split("\\s");
        getFilenamesFromArgs(args);
    }

    private boolean isResourcesThreadsFinished() {
        for(Thread res_thread : res_threads){
            if (res_thread.isAlive() && !res_thread.isInterrupted())
                return false;
        }
        return true;
    }

}
