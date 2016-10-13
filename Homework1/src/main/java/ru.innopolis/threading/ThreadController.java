package ru.innopolis.threading;

/**
 * Created by Smith on 13.10.2016.
 */
public class ThreadController {
    public void aThreadIsOver(){
        this.notifyAll();
    }
}
