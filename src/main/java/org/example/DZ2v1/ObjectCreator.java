package org.example.DZ2v1;

import java.lang.reflect.Constructor;

public class ObjectCreator {

    public static <T> T createObj(Class<T> tClass) {
        try {
            Constructor<T> constructor = tClass.getDeclaredConstructor();

            T obj = constructor.newInstance();
            RandomAnnotationProcessor.processAnnotation(obj);
            return obj;
        } catch (Exception e) {
            System.err.println("ничего не получилось: " + e.getMessage());
            return null; // throw new IllegalStateException
        }
    }

    public static <T> T createObjForData(Class<T> tClass) {
        try {
            Constructor<T> constructor = tClass.getDeclaredConstructor();

            T obj = constructor.newInstance();
            RandomAnnotationProcessor.processAnnotationForData(obj);
            return obj;
        } catch (Exception e) {
            System.err.println("ничего не получилось: " + e.getMessage());
            return null; // throw new IllegalStateException
        }
    }
}
