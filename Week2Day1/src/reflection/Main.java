package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * Created by Smith on 10.10.2016.
 */
public class Main {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Class<TypeForTest> clazz = TypeForTest.class;

        for(Constructor constructor : clazz.getConstructors()){
            System.out.println(constructor);
            constructor.setAccessible(false);
        }

        TypeForTest typeForTest = clazz.newInstance();
        for(Field f : clazz.getFields())
            System.out.println(f);
        for(Field f : clazz.getDeclaredFields()) {
            f.setAccessible(true);
            f.set(typeForTest, 10);
            System.out.println(f + ", value: " + f.get(typeForTest));
        }
    }
}
