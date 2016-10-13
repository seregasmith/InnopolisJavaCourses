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
 * Created by Smith on 13.10.2016.
 */
public class Generator {
    public static final String OUTPUT_DIR = "C:\\Temp\\";
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
        for(int ind = 0; i < strings_count; i++){
            int values_count = ThreadLocalRandom.current().nextInt(MIN_VALUES_PER_STRING, MAX_VALUES_PER_STRING + 1);
            List<String> strings = new ArrayList<>();
            Files.write(Paths.get(String.valueOf(ind)), strings);
        }
    }

    private static void checkOutputDir() {
        if (Files.notExists(OUTPUT_DIR)) {
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
