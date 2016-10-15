package ru.innopolis.threading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import ru.innopolis.resource.Resource;
import ru.innopolis.resource.Summator;
import ru.innopolis.utils.LogUtils;
import ru.innopolis.utils.Validator;


/**
 * Thread behavior.
 * A task to get value from resource abstraction and decide what to do with it.
 * Created by Smith on 12.10.2016.
 */
public class ResourceReader implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(ResourceReader.class);
    private Validator validator;
    private Resource<Integer> resource;

    {
        MDC.put(LogUtils.PACKAGE_KEY, ResourceReader.class.getPackage().getName());
        MDC.put(LogUtils.CLASS_KEY, ResourceReader.class.getSimpleName());
    }

    public ResourceReader(Resource<Integer> resource, Validator validator) {
        this.validator = validator;
        this.resource = resource;
    }




    public void run() {
        MDC.put(LogUtils.METHOD_KEY, "run()");
        logger.info("{} for {} is started.",ResourceReader.class.getName(),resource);
        Integer value = null;
        while (!Thread.currentThread().isInterrupted()){
            try {
                value = resource.nextValue();
                if(value == null)
                    break;
                if(validator.isValid(value)){
                    Summator.getInstance().add(value);
                }
            } catch (Exception e) {
                logger.error("Exception: Message: {}, StackTrace: {}", e.getMessage(), e.getStackTrace());
                ThreadsController.getInstance().interruptAll();
            }
        }
        synchronized (this) {
            logger.info("{} for {} is over.", ResourceReader.class.getName(), resource);
            ThreadsController.getInstance().notifyAThreadIsOver(this);
        }
    }
}
