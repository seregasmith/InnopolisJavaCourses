package ru.innopolis;

import org.apache.log4j.Logger;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.*;
import org.slf4j.LoggerFactory;
import ru.innopolis.streams.StreamWriter;


/**
 * Created by Smith on 13.10.2016.
 */
public class HelloWorldTest {
    private Mockery context;
    private HelloWorld hw;
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(HelloWorldTest.class);

    @Before
    public void before(){
        logger.info("before method");
        this.hw = new HelloWorld();
        this.context = new JUnit4Mockery();
    }


    @Test
    public void testIsHelloWorld(){
        logger.info("This is testIsHelloWorld");
        Assert.assertTrue(hw.isHelloWorld("Hello world"));
    }

    @Test
    public void testHandle(){
        logger.info("Test handle");
        StreamWriter streamWriter = context.mock(StreamWriter.class);
        context.checking(new Expectations(){{
            oneOf(streamWriter).write("Tatarstan");
            will(returnValue(new Long(16)));
        }});
        hw.setStreamWriter(streamWriter);
        Assert.assertEquals(new Long(16), hw.handle("Tatarstan"));
    }

}
