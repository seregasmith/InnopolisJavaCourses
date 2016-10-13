package ru.innopolis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Created by Smith on 12.10.2016.
 */
public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            MDC.put("userName", "Smith");
            MDC.put("userRole", "admin");
            Integer i = 2;
            logger.info("Чётное: {}", i);
        }finally {
            MDC.clear();
        }
    }

}
