package ru.innopolis.resource;

import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import ru.innopolis.threading.ThreadsController;
import ru.innopolis.utils.LogUtils;
import ru.innopolis.utils.Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
/**
 * File resource abstraction
 * Created by Smith on 13.10.2016.
 */
public class FileResource<T> implements Resource<T> {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(FileResource.class);
    private BufferedReader reader;
    private String filename;
    private T[] values;
    private int position = 0;

    {
        MDC.put(LogUtils.PACKAGE_KEY, FileResource.class.getPackage().getName());
        MDC.put(LogUtils.CLASS_KEY, FileResource.class.getSimpleName());
    }

    public FileResource(BufferedReader reader, String filename) {
        this.reader = reader;
        this.filename = filename;
    }

    public FileResource(String filename) {
        MDC.put(LogUtils.METHOD_KEY, "FileResource(String filename)");
        try {
            reader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            logger.error("{}", e.getMessage());
            ThreadsController.getInstance().interruptAll();
        }
        this.filename = filename;
    }

    /**
     * Get a next value from resource
     * @return value, stored by next in resource. Can be null
     * @throws Exception - in case the problem becomes during the getting next value
     */
    @Override
    public synchronized T nextValue() throws Exception {
        if( values != null && position < values.length ){
            return values[position++];
        }
        if( readNextString() ){
            return values[position++];
        }
        return null;
    }

    private boolean readNextString() throws Exception {
        String line = reader.readLine();
        if(line == null)
            return false;
        this.values = Utils.getValuesFromLine(line);
        this.position = 0;
        return true;
    }

    @Override
    public String toString() {
        return "FileResource{" +
                "filename='" + filename + '\'' +
                '}';
    }
}
