package ru.innopolis.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import ru.innopolis.utils.LogUtils;
import ru.innopolis.utils.Observable;
import ru.innopolis.utils.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Store and add integer values.
 * Singleton.
 * ConcreteObservable in Observer Design Pattern
 * Created by Smith on 12.10.2016.
 */
public class Summator implements Observable {
    private static Logger logger = LoggerFactory.getLogger(Summator.class);
    private static final Summator instance = new Summator();

    public int getTotal() {
        return total;
    }

    public List<Observer> getObservers() {
        return observers;
    }

    private volatile int total;
    private List<Observer> observers = new ArrayList<>();
    {
        MDC.put(LogUtils.PACKAGE_KEY, Summator.class.getPackage().getName());
        MDC.put(LogUtils.CLASS_KEY, Summator.class.getSimpleName());
    }

    private Summator(){}

    public synchronized void add(int value){
        MDC.put(LogUtils.METHOD_KEY, "add(int value)");
        logger.debug("Added value {}", value);
        total += value;
        logger.debug("Total becomes {}", total);
        notifyObserver();
    }


    public static Summator getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "Summator{" +
                "total=" + total +
                '}';
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObserver() {
        for (Observer observer : observers) {
            observer.handleEvent();
        }
    }
}
