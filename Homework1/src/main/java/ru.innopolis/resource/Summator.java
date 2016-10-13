package ru.innopolis.resource;

/**
 * Created by Smith on 12.10.2016.
 */
public class Summator {
    private static final Summator instance = new Summator();

    private Summator(){}

    private volatile int total;
    private volatile int prev;

    public synchronized void add(int value){
        total += value;
        prev = value;
        System.out.println("Added: " + value + "; Total:" + total);
//        this.notify();
    }

    public static Summator getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "Summator{" +
                "total=" + total +
                ", prev_add=" + prev +
                '}';
    }
}
