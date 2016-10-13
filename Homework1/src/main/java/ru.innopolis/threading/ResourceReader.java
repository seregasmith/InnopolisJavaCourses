package ru.innopolis.threading;

import ru.innopolis.resource.Summator;
import ru.innopolis.utils.Utils;
import ru.innopolis.utils.Validator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Smith on 12.10.2016.
 */
public class ResourceReader implements Runnable {
    private Validator validator;
    private final String filename;

    public ResourceReader(String filename){
        this.filename = filename;
    }

    public ResourceReader(String filename, Validator validator){
        this.filename = filename;
        this.validator = validator;
    }


    public void run() {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace(); // TODO set logging
        }
        for(String line: lines){
            Integer[] values = Utils.getIntegersFormLine(line);
            for(Integer i : values){
                if(validator.isValid(i)){
                    Summator.getInstance().add(i);
                }
            }
        }
    }
}
