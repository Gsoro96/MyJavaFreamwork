package org.example;

import org.example.beanloader.MyBeanLoader;
import org.example.container.MyBeanContainer;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        MyBeanContainer beanContainer = new MyBeanContainer();
        MyBeanLoader beanLoader = new MyBeanLoader(beanContainer);
        beanLoader.loadBeans("./target/classes");

        System.out.println(beanContainer.getBeans());
    }
}