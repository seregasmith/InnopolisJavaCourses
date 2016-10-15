package ru.innopolis;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.innopolis.resource.FileResource;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Smith on 14.10.2016.
 */
public class FileResourceTest {
    FileResource<Integer> fileResource;
    private Mockery context;
    private BufferedReader reader;

    @Before
    public void createMock(){
        this.context = new JUnit4Mockery(){{
            setImposteriser(ClassImposteriser.INSTANCE);
        }};

        reader = context.mock(BufferedReader.class);
        context.checking(new Expectations(){{
            try {
                oneOf(reader).readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            will(returnValue("321 43 43"));
        }});
    }

    @Before
    public void createFileResource(){
        fileResource = new FileResource<Integer>(reader,"Test.txt");
    }

    @Test
    public void testNextValue(){
        try {
            Assert.assertEquals(new Integer(321),fileResource.nextValue());
            Assert.assertEquals(new Integer(43),fileResource.nextValue());
            Assert.assertEquals(new Integer(43),fileResource.nextValue());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
