package org.example;

import org.example.beanloader.MyBeanLoader;
import org.example.beans.Car;
import org.example.container.MyBeanContainer;
import org.example.injection.Injector;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // Create Bean Container
        MyBeanContainer beanContainer = new MyBeanContainer();
        MyBeanLoader beanLoader = new MyBeanLoader(beanContainer);
        // Load all beans in bean Container
        beanLoader.loadBeans("./target/classes");
        //Perform Dependency Injection
        Injector injector = new Injector(beanContainer);
        injector.performDependencyInjection();


        Car bean = (Car) beanContainer.getBean(Car.class);
        bean.makeSomeNoise();
    }
}