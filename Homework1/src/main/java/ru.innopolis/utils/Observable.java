package ru.innopolis.utils;

/**
 * Created by Smith on 14.10.2016.
 */
public interface Observable {
    void addObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObserver();
}
