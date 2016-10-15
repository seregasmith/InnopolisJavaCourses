package ru.innopolis.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Smith on 12.10.2016.
 */
public class Utils {

    /**
     * Note: the method call getIntegersFromLine
     * without any condition check. The problem in a diamond operator,
     * we cannot override static generic method and
     * we haven't any opportunity to know, that type is T.
     * Need for remodeling.
     * @param line - a line for parsing, to get values
     * @param <T>
     * @return
     * @throws NumberFormatException
     */
    public static <T>T[] getValuesFromLine(String line) throws NumberFormatException{
//        if(Integer.class.equals(type)){
        try {
            return (T[]) getIntegersFromLine(line);
        }catch (ClassCastException e){
            throw new RuntimeException("Not supported");
        }

//        }
//        throw new RuntimeException("Not supported for " + type.getName());
    }

    private static Integer[] getIntegersFromLine(String line) throws NumberFormatException {
        String[] strings = line.split("\\s");
        List<Integer> values = new ArrayList<>();
        for(String string : strings){
            try {
                values.add(Integer.valueOf(string));
            }catch (NumberFormatException e){
                throw e;
            }
        }
        return values.toArray(new Integer[values.size()]);
    }

}
