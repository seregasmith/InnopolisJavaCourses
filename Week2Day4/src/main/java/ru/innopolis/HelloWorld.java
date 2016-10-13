package ru.innopolis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Smith on 13.10.2016.
 */
public class HelloWorld {
    private static Logger logger = LoggerFactory.getLogger(HelloWorld.class);
    public boolean isHelloWorld(String arg){
        return "Hello world".equals(arg);
    }

    public static void main(String[] args) {

    }
}
