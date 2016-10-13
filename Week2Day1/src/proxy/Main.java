package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Smith on 10.10.2016.
 */
public class Main {
    public static void main(String[] args) {
        Human pureHuman = getHuman();
        Human humanProxy = createHuman(new HumanImplementation());
        Human humanDelegate = new HumanDelegate();

        long timestamp = System.currentTimeMillis();
        loop(humanProxy);
        System.out.println("Proxy invoked for " + (System.currentTimeMillis() - timestamp));

        timestamp = System.currentTimeMillis();
        loop(pureHuman);
        System.out.println("Pure entity invoked for " + (System.currentTimeMillis() - timestamp));

        timestamp = System.currentTimeMillis();
        loop(humanDelegate);
        System.out.println("Delegate invoked for " + (System.currentTimeMillis() - timestamp));

    }

    static Human getHuman(){
        return new HumanImplementation();
    }

    static Human createHuman(Human human){
        ClassLoader classLoader = Main.class.getClassLoader();
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                System.out.println(method.getName());
                return method.invoke(human, args);
            }
        };
        return (Human) Proxy.newProxyInstance(
                classLoader,
                new Class[]{Human.class},
                invocationHandler
        );
    }

    static void loop(Human human){
        for( int i = 0; i < 10_000_000; i++ ){
            human.setAge((int) Math.random()*100);
        }
    }
}
