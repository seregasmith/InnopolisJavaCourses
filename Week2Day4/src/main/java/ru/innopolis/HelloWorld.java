package ru.innopolis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.streams.StreamWriter;
import ru.innopolis.streams.WSStreamWrite;

/**
 * Created by Smith on 13.10.2016.
 */
public class HelloWorld {
    private static Logger logger = LoggerFactory.getLogger(HelloWorld.class);

    public StreamWriter getStreamWriter() {
        return streamWriter;
    }

    public void setStreamWriter(StreamWriter streamWriter) {
        this.streamWriter = streamWriter;
    }

    private StreamWriter streamWriter = new WSStreamWrite();
    public boolean isHelloWorld(String arg){
        return "Hello world".equals(arg);
    }

    public static void main(String[] args) {

    }

    public Long handle(String msg){
        Long key = this.streamWriter.write(msg);
        logger.info("Method handle {}", key);
        return key;
    }
}
