package org.example.utils;



import java.lang.reflect.Field;
import java.util.Arrays;

public final class MyBeanUtils {

    private MyBeanUtils(){}

    public static boolean objectHasAnnotationOfType(Object target, Class<?> clazz){
        return Arrays.stream(target.getClass().getAnnotations()).anyMatch(annotation -> annotation.annotationType().equals(clazz));
    }

    public static boolean fieldHasAnnotationOfType(Field target, Class<?> clazz){
        return Arrays.stream(target.getAnnotations()).anyMatch(annotation -> annotation.annotationType().equals(clazz));
    }

    public static boolean classHasAnnotationOfType(Class<?> target, Class<?> clazz){
        return Arrays.stream(target.getAnnotations()).anyMatch(annotation -> annotation.annotationType().equals(clazz));
    }
}
