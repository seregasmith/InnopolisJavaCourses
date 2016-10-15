package ru.innopolis;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.resource.FileResource;
import ru.innopolis.resource.Summator;
import ru.innopolis.threading.ResourceReader;
import ru.innopolis.threading.ThreadsController;
import ru.innopolis.threading.TotalWriter;
import ru.innopolis.utils.Validator;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Smith on 12.10.2016.
 */
public class Application {
    private static Logger logger = LoggerFactory.getLogger(Application.class);
    private List<String> files = new ArrayList<>();     // a list of filenames, given from args or manually asked from user
    private Validator validator = i -> (i%2==0) && (i>0);

    public Application(String[] args){
        if(args.length > 0){
            getFilenamesFromArgs(args);
        }else {
            getFilenamesFromUser();
        }
    }

    public void run(){
        Summator summator = Summator.getInstance();
        TotalWriter totalWriter = new TotalWriter();
        summator.addObserver(totalWriter);
        ThreadsController.getInstance().addDaemon(new Thread(totalWriter));
        new Thread(ThreadsController.getInstance()).start();
        ThreadsController.getInstance().startDaemons();
        for(String filename : files){
            logger.info("File as argument: {}", filename);
            Thread thread = new Thread(new ResourceReader(new FileResource<>(filename), validator));
            ThreadsController.getInstance().addThread(thread);
        }
        ThreadsController.getInstance().startAll();
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
            if(arg.endsWith("*")){
                File dir = new File(".");
                FileFilter fileFilter = new WildcardFileFilter(arg);
                File[] files = dir.listFiles(fileFilter);
                for (int i = 0; i < files.length; i++) {
                    this.files.add(files[i].getAbsolutePath());
                }
            }
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

    public static void main(String[] args) {
        Application app = new Application(args); // init app
        app.run();
    }

}
