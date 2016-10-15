package ru.innopolis.generator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * There's a script for generation right files for the task
 * Created by Smith on 13.10.2016.
 */
public class Generator {
    public static final String OUTPUT_DIR = "C:\\Temp\\";
    public static final String FILE_PREFIX = "file";
    public static final String FILE_POSTFIX = ".txt";
    public static final int FILES_COUNT = 100;
    public static final int MAX_STRING_COUNT = 1000;
    public static final int MIN_STRING_COUNT = 100;
    public static final int MAX_VALUES_PER_STRING = 100;
    public static final int MIN_VALUES_PER_STRING = 20;
    public static final int MIN_VALUE = -1000;
    public static final int MAX_VALUE = 1000;

    public static void main(String[] args) {
        // if output dir doesn't exist create
        checkOutputDir();
        for(int i = 0; i < FILES_COUNT; i++){
            try {
                generateFile(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void generateFile(int i) throws IOException {
        int strings_count = ThreadLocalRandom.current().nextInt(MIN_STRING_COUNT, MAX_STRING_COUNT + 1);
        List<String> strings = new ArrayList<>();
        for(int ind = 0; ind < strings_count; ind++){
            int values_count = ThreadLocalRandom.current().nextInt(MIN_VALUES_PER_STRING, MAX_VALUES_PER_STRING + 1);
            String string = "";
            for(int val_ind = 0; val_ind < values_count; val_ind++){
                int value = ThreadLocalRandom.current().nextInt(MIN_VALUE,MAX_VALUE + 1);
                string += value;
                if(val_ind != values_count - 1){
                    string += " ";
                }
            }
            strings.add(string);
        }

        String file_path = OUTPUT_DIR + FILE_PREFIX + String.valueOf(i) + FILE_POSTFIX;
        Files.write(Paths.get(file_path), strings);
    }

    private static void checkOutputDir() {
        if ( Files.notExists( Paths.get(OUTPUT_DIR) ) ) {
            // create dir
            new File(OUTPUT_DIR).mkdir();
        }else{
            // cleaning dir
            File dir = new File(OUTPUT_DIR);
            for(File file: dir.listFiles())
                if (!file.isDirectory())
                    file.delete();
        }
    }
}
