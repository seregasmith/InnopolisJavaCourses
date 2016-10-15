package ru.innopolis;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.innopolis.resource.Summator;
import ru.innopolis.threading.TotalWriter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

/**
 * Created by Smith on 14.10.2016.
 */
public class TotalWriterTest {
    TotalWriter totalWriter = null;

    @Before
    public void createTotalWriter(){
        totalWriter = new TotalWriter();
    }

    @Test (timeout = 1_000)
    public void testHandleEvent(){
        Thread thread = new Thread(totalWriter);
        thread.start();
        try {
            sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();

        System.setOut(new java.io.PrintStream(out));
        synchronized (totalWriter) {
            totalWriter.handleEvent();
        }
        try {
            sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String actual = new String(out.toString() + "");

        Assert.assertTrue(actual.length()>3);
        thread.interrupt();
    }

    @Test
    public void testRun(){
        new Thread(totalWriter).start();
        Summator summator = Summator.getInstance();
        summator.addObserver(totalWriter);
        try {
            sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();

        System.setOut(new java.io.PrintStream(out));

        for (int i = 0; i < 20; i++) {
            summator.add(i);
        }

        try {
            sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.setOut(System.out);
        String actual = new String(out.toString() + "");
        actual = actual.substring(0,actual.length()-2); // delete \r\n

        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(actual);
        Integer last = null;
        while(m.find()) {
            last = Integer.valueOf(m.group());
        }

        Assert.assertEquals(new Integer(190), last);
    }
}
