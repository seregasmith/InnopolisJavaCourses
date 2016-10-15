package ru.innopolis;

import org.hamcrest.Description;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.api.Action;
import org.jmock.api.Invocation;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.innopolis.threading.ThreadsController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Created by Smith on 14.10.2016.
 */
public class ThreadsControllerTest {
    private ThreadsController threadsController;
    private Mockery context;
    private Runnable runnable;

    @Before
    public void createThreadsController(){
        threadsController = ThreadsController.getInstance();
        this.context = new JUnit4Mockery(){{
            setThreadingPolicy(new Synchroniser());
        }};
        runnable = context.mock(Runnable.class);
        context.checking(new Expectations(){{
            try {
                allowing(runnable).run();
            } catch (Exception e) {
                throw e;
            }
            will(new Action() {
                @Override
                public void describeTo(Description description) {

                }

                @Override
                public Object invoke(Invocation invocation){

                    try {
                        sleep(20_000);
                    } catch (InterruptedException e) {
//                        e.printStackTrace();
                    }
                    return null;
                }
            });
        }});
    }

    @Test
    public void testAddDaemon(){
        int before = threadsController.getDaemons().size();
        int n = 5;
        for (int i = 0; i < n; i++) {
            threadsController.addDaemon(new Thread(runnable));
        }
        Assert.assertEquals(before+n,threadsController.getDaemons().size());
        threadsController.getDaemons().clear();
    }

    @Test
    public void testAddThread(){
        int before = threadsController.getThreads().size();
        int n = 5;
        for (int i = 0; i < n; i++) {
            threadsController.addThread(new Thread(runnable));
        }
        Assert.assertEquals(before+n,threadsController.getThreads().size());
        threadsController.getThreads().clear();
    }

    @Test (timeout = 4_000)
    public void testStartDaemons() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            threadsController.addDaemon(new Thread(runnable));
        }
        threadsController.startDaemons();
        for(Thread thread : threadsController.getDaemons()){
            if(!thread.isAlive())Assert.fail("Thread is not started");
        }
        threadsController.interruptAll();
        sleep(2_000);
        threadsController.getDaemons().clear();
    }

    @Test (timeout = 4_000)
    public void testStartAll() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            threadsController.addThread(new Thread(runnable));
        }
        threadsController.startAll();
        for(Thread thread : threadsController.getThreads()){
            if(!thread.isAlive())Assert.fail("Thread is not started");
        }
        threadsController.interruptAll();
        sleep(2_000);
        threadsController.getThreads().clear();
    }

    @Test
    public void testInterruptAll() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            threadsController.addThread(new Thread(runnable));
        }
        threadsController.startAll();
        threadsController.interruptAll();
        sleep(200);
        for(Thread thread : threadsController.getThreads()){
            if(thread.isAlive())Assert.fail("Thread is not interrupted");
        }
        threadsController.getThreads().clear();
    }

    @Test (timeout = 4_000)
    public void testNotifyAThreadIsOver(){
        threadsController.getThreads().clear();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            threads.add(new Thread(runnable));
        }
        for (Thread thread : threads) {
            threadsController.addThread(thread);
        }
        threadsController.startAll();
        try {
            Field isWakedUpField = ThreadsController.class.getDeclaredField("isWakedUp");
            isWakedUpField.setAccessible(true);
            for (Thread thread : threads) {
                isWakedUpField.set(threadsController,true);
                threadsController.notifyAThreadIsOver(thread);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(0, threadsController.getThreads().size());
    }

    @Test (timeout = 5_000)
    public void testRun() throws InterruptedException {
        try {
            ThreadsController.getInstance().addDaemon(new Thread(runnable));
            new Thread(ThreadsController.getInstance()).start();
            ThreadsController.getInstance().startDaemons();
            for (int i = 0; i < 5_000; i++) {
                Thread thread = new Thread(runnable);
                ThreadsController.getInstance().addThread(thread);
            }
            ThreadsController.getInstance().startAll();
            sleep(2_000);
            ThreadsController.getInstance().interruptAll();
            threadsController.getThreads().clear();
            threadsController.getDaemons().clear();
        }catch (Exception e){
            e.printStackTrace();
            Assert.fail();
        }
    }
}
