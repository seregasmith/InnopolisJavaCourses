package ru.innopolis.threading;

import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import ru.innopolis.resource.Summator;
import ru.innopolis.utils.LogUtils;
import ru.innopolis.utils.Observer;

/**
 * Thread behaviour.
 * ConcreteObserver in Observer Design Pattern.
 * A task to follow summator and print actual state of summator.
 * Created by Smith on 12.10.2016.
 */
public class TotalWriter implements Runnable, Observer {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TotalWriter.class);
    {
        MDC.put(LogUtils.PACKAGE_KEY, TotalWriter.class.getPackage().getName());
        MDC.put(LogUtils.CLASS_KEY, TotalWriter.class.getSimpleName());
    }

    @Override
    public synchronized void handleEvent() {
        notify();
    }

    @Override
    public void run() {
        MDC.put(LogUtils.METHOD_KEY, "run()");
        logger.info("{} is started.",TotalWriter.class.getName());
        while (!Thread.currentThread().isInterrupted()){
            synchronized (this){
                try {
                    wait();
                    System.out.println(Summator.getInstance());
                    synchronized (ThreadsController.getInstance()) {
                        ThreadsController.getInstance().notifyAll();
                    }
                } catch (InterruptedException e) {
                    logger.warn(e.getMessage());
                }
            }
        }
        logger.info("{} is over.", TotalWriter.class.getName());
    }
}
