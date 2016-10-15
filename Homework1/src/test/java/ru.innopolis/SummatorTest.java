package ru.innopolis;

import org.hamcrest.Description;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.api.Action;
import org.jmock.api.Invocation;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.innopolis.resource.Summator;
import ru.innopolis.utils.Observer;

/**
 * Created by Smith on 14.10.2016.
 */
public class SummatorTest {
    private Summator summator;
    private Mockery context;
    private static Integer left = new Integer(0);

    @Before
    public void getSummator(){
        summator = Summator.getInstance();
    }

    @Before
    public void createMock(){
        this.context = new JUnit4Mockery();
    }

    @Test
    public void testAdd(){
        Integer before = summator.getTotal();
        summator.add(666);
        Assert.assertEquals(before+666,summator.getTotal());
    }

    @Test
    public void testAddObserver(){
        Observer observer = context.mock(Observer.class);
        int before_length = summator.getObservers().size();
        int n = 5;
        for (int i = 0; i < n; i++) {
            summator.addObserver(observer);
        }
        Assert.assertEquals(before_length+n,summator.getObservers().size());
    }

    public static Action add(Integer right) {
        return new Action() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Add to left right value").appendValue(right);
            }

            @Override
            public Object invoke(Invocation invocation) throws Throwable {
                left += right;
                return null;
            }
        };
    }


    @Test
    public void testNotifyObserver(){
        Observer observer = context.mock(Observer.class);
        int before = left;
        int n = 6;
        context.checking(new Expectations(){{
            oneOf(observer).handleEvent();
            will(add(n));
        }});

        summator.addObserver(observer);
        summator.notifyObserver();
        Assert.assertEquals(new Integer(before+n),left);
    }

    @Test
    public void testRemoveObserver(){
        Observer observer = context.mock(Observer.class);
        int before_length = summator.getObservers().size();
        int n = 5;
        for (int i = 0; i < n; i++) {
            summator.addObserver(observer);

        }
        for (int i = 0; i < n; i++) {
            summator.removeObserver(observer);

        }
        Assert.assertEquals(before_length,summator.getObservers().size());
    }
}
