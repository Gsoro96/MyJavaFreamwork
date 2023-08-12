package org.example;

import org.example.beans.loader.MyBeanLoader;
import org.example.beans.Car;
import org.example.beans.container.MyBeanContainer;
import org.example.beans.injection.Injector;
import org.example.myhibernate.EntityCreator;
import org.example.myhibernate.container.EntitiesContainer;
import org.example.myhibernate.loader.EntitiesLoader;

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


        EntitiesContainer entitiesContainer = new EntitiesContainer();
        EntitiesLoader entitiesLoader = new EntitiesLoader(entitiesContainer);
        entitiesLoader.loadEntities("./target/classes");
        EntityCreator entityCreator = new EntityCreator(entitiesContainer);
        entityCreator.createEntities();
    }
}