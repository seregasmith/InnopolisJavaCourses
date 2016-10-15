package ru.innopolis.resource;

/**
 * Created by Smith on 13.10.2016.
 */
public interface Resource<T> {
    T nextValue() throws Exception;
}
