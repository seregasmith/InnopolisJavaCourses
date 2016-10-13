package ru.innopolis.resource;

/**
 * Created by Smith on 12.10.2016.
 */
public class Summator {
    private static Summator instance;

    private Summator(){}

    private int total;

    public void add(int value){
        synchronized (this) {
            total += value;
        }
        synchronized (this){
            this.notifyAll();
        }
    }

    public static Summator getInstance() {
        if(instance == null)
            instance = new Summator();
        return instance;
    }

    @Override
    public String toString() {
        return "Summator{" +
                "total=" + total +
                '}';
    }
}
