package ru.innopolis.threading;

import ru.innopolis.resource.Summator;
import ru.innopolis.utils.Utils;

/**
 * Created by Smith on 12.10.2016.
 */
public class TotalWriter implements Runnable {
    public void run() {
        Summator summator = Summator.getInstance();
        While:
        while (!Utils.isInterrupted(Thread.currentThread())){
            synchronized (summator){
                System.out.println(summator);
                try {
                    summator.wait();
                } catch (InterruptedException e) {
//                    e.getMessage();
                    break While;
                }
            }
        }
        // TODO: 13.10.2016 log ending
    }
}
