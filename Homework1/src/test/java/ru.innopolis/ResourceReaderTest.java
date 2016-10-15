package ru.innopolis;

import org.hamcrest.Description;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.api.Action;
import org.jmock.api.Invocation;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.Before;
import org.junit.Test;
import ru.innopolis.resource.Resource;
import ru.innopolis.resource.Summator;
import ru.innopolis.threading.ResourceReader;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Smith on 14.10.2016.
 */
public class ResourceReaderTest {
    private ResourceReader resourceReader;
    private Mockery context;
    private Resource<Integer> resource;

    private static int counter = 0;

    @Before
    public void createResourceReader() throws Exception {
        this.context = new JUnit4Mockery(){{
            setThreadingPolicy(new Synchroniser());
        }};
        resource = context.mock(Resource.class);
        context.checking(new Expectations(){{
            try {
                allowing(resource).nextValue();
            } catch (Exception e) {
                throw e;
            }
            will(new Action() {
                @Override
                public void describeTo(Description description) {

                }

                @Override
                public Object invoke(Invocation invocation) throws Throwable {
                    if(counter++ < 20)
                        return new Integer(5);
                    return null;
                }
            });
        }});
        resourceReader = new ResourceReader(
                resource,
                i -> i==5
        );
    }

    @Test (timeout = 10_000)
    public void testRun(){
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            threads.add(new Thread(resourceReader));
        }
        for (Thread thread : threads){
            thread.start();
        }
        try {
            sleep(1_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            fail();
        }
        assertEquals(20*5, Summator.getInstance().getTotal());
    }
}
