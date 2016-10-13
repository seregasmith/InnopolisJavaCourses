package ru.innopolis.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by Smith on 12.10.2016.
 */
public class Utils {
    public static boolean isInterrupted(Thread thread){
        return thread.isInterrupted();
    }

    public static Integer[] getIntegersFormLine(String line) {
        String[] strings = line.split("\\s");
        List<Integer> values = new ArrayList<>();
        for(String string : strings){
            try {
                values.add(Integer.valueOf(string));
            }catch (NumberFormatException e){
                // TODO Solve, what to do with wrong symbol in line
            }
        }
        return values.toArray(new Integer[values.size()]);
    }
}
