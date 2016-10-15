package ru.innopolis.threading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import ru.innopolis.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Thread behavior.
 * Singleton.
 * A task to manage threads. Can work with daemons - threads do not have an exit by itself.
 * Created by Smith on 14.10.2016.
 */
public class ThreadsController implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(ThreadsController.class);
    private static final ThreadsController instance = new ThreadsController();
    List<Thread> threads = new CopyOnWriteArrayList<>();
    List<Thread> daemons = new ArrayList<>();

    public List<Thread> getThreads() {
        return threads;
    }

    public List<Thread> getDaemons() {
        return daemons;
    }

    private volatile boolean isStarted;
    private volatile boolean isWakedUp;

    {
        MDC.put(LogUtils.PACKAGE_KEY, ThreadsController.class.getPackage().getName());
        MDC.put(LogUtils.CLASS_KEY, ThreadsController.class.getSimpleName());
    }

    private ThreadsController(){}

    public static ThreadsController getInstance(){
        return instance;
    }

    @Override
    public void run() {
        MDC.put(LogUtils.METHOD_KEY, "run()");
        logger.info("{} is started.", ThreadsController.class.getName());
        while (!isStarted) {
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    logger.error("Exception: Message: {}, StackTrace: {}", e.getMessage(), e.getStackTrace());
                    break;
                }
            }
        }
        while (!Thread.currentThread().isInterrupted() && !isAllThreadsFinished()) {
            synchronized (this) {
                try {
                    isWakedUp = false;
                    wait();
                    isWakedUp = true;
                } catch (InterruptedException e) {
                    logger.error("Exception: Message: {}, StackTrace: {}", e.getMessage(), e.getStackTrace());
                    break;
                }
            }
        }
        for(Thread daemon : daemons){
            while (daemon.isAlive()){
                synchronized (daemon){
                    daemon.interrupt();
                    daemon.notify();
                }
            }
        }
        logger.info("{} is over.", ThreadsController.class.getName());
    }

    public synchronized void addThread(Thread thread){
        threads.add(thread);
    }

    public synchronized void addDaemon(Thread thread){
//        thread.setDaemon(true); // not well, because daemon should be interrupted gently
        daemons.add(thread);
    }

    private synchronized boolean isAllThreadsFinished() {
        MDC.put(LogUtils.METHOD_KEY, "isAllThreadsFinished()");
        boolean fl = true;
        int count = 0;
        for(Thread thread : threads){
            if (thread != null && thread.isAlive()) {
                fl = false;
                count++;
            }
        }
        if (fl)
            logger.warn("All threads are finished");
        else {
            logger.debug("Not finished threads count: {}", count);
            if(count == 1){
                for(Thread thread : threads){
                    logger.debug("isAlive:{}", thread.isAlive());
                }
            }
        }
        return fl;
    }

    public synchronized void interruptAll(){
        MDC.put(LogUtils.METHOD_KEY, "interruptAll()");
        for(Thread thread : threads) {
            thread.interrupt();
        }
        isStarted = false;
        logger.info("All threads are interrupted.");
        notify();
    }

    public synchronized void startAll(){
        MDC.put(LogUtils.METHOD_KEY, "startAll()");
        for(Thread thread : threads) {
            thread.start();
        }
        isStarted = true;
        logger.info("All threads are started.");
        notify();
    }

    public synchronized void startDaemons() {
        for(Thread d : daemons){
            d.start();
        }
    }

    public void notifyAThreadIsOver(Object o) {
        threads.remove(o);
        while (!isWakedUp) {
            synchronized (this) {
                notify();
            }
        }

    }
}
