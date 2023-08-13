package org.example.beans.beanloader;

import org.example.beans.container.MyBeanContainer;
import org.example.beans.loader.MyBeanLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MyBeanLoaderTest {

    static MyBeanLoader myBeanLoader;
    static MyBeanContainer myBeanContainer;

    @BeforeAll
    static void setup() {
        myBeanContainer = new MyBeanContainer();
        myBeanLoader = new MyBeanLoader(myBeanContainer);
    }


    @Test
    void test_when_loadBeans_is_called_that_populates_myBeanContainer_properly() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String classpath = "./target/classes";
        List<String> beans = new ArrayList<>();
        beans.add("Car");
        beans.add("DieselMotor");
        beans.add("PetrolMotor");

        myBeanLoader.loadBeans(classpath);

        assertEquals(beans.size(), myBeanContainer.getBeans().size());
        myBeanContainer.getBeans().forEach(bean -> assertTrue(beans.contains(bean.getClass().getSimpleName())));
    }
}