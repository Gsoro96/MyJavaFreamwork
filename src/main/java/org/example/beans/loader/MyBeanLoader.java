package org.example.beans.loader;

import org.example.annotations.MyBean;
import org.example.beans.container.MyBeanContainer;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.example.classloader.MyClassLoader;

import static org.example.utils.MyBeanUtils.classHasAnnotationOfType;


public class MyBeanLoader {
    private final MyBeanContainer beanContainer;

    public MyBeanLoader(MyBeanContainer beanContainer) {
        this.beanContainer = beanContainer;
    }

    public void loadBeans(String classPath) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Set<Class<?>> classes = MyClassLoader.loadAllClassesInClassPath(classPath);
        for (Class<?> clazz : classes) {
            if (classHasAnnotationOfType(clazz, MyBean.class)) {
                beanContainer.registerBean(clazz.getConstructor().newInstance());
            }
        }
    }
}
