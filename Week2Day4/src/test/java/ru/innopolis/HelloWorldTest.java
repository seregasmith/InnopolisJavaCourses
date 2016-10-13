package ru.innopolis;

import org.apache.log4j.Logger;
import org.junit.*;
import org.slf4j.LoggerFactory;


/**
 * Created by Smith on 13.10.2016.
 */
public class HelloWorldTest {
    private HelloWorld hw = new HelloWorld();
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(HelloWorldTest.class);

    @BeforeClass
    public static void beforeTest(){
        logger.info("This is @BeforeClass method");
    }

    @Test
    public void testIsHelloWorld(){
        logger.info("This is testIsHelloWorld");
        Assert.assertTrue(hw.isHelloWorld("Hello world"));
    }

    @Before
    public void before(){
        logger.info("This is @Before method");
    }

    @After
    public void after(){
        logger.info("This is @After method");
    }

    @AfterClass
    public static void afterTest(){
        logger.info("This is @AfterClass method");
    }
}
