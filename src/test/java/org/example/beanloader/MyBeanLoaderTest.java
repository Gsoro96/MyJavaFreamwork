package org.example.beanloader;

import org.example.container.MyBeanContainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MyBeanLoaderTest {

    static MyBeanLoader myBeanLoader;
    static MyBeanContainer myBeanContainer;

    @BeforeAll
    static void setup() {
        myBeanContainer = new MyBeanContainer();
        myBeanLoader = new MyBeanLoader(myBeanContainer);
    }

    @Test
    void test_when_loadAllClassesInClassPath_is_called_with_invalid_classpath_RuntimeException_is_thrown() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String classpath = "";
        Set<Class<?>> result = new HashSet<>();
        Method loadAllClassesInClassPath = myBeanLoader.getClass().getDeclaredMethod("loadAllClassesInClassPath", String.class, Set.class);
        loadAllClassesInClassPath.setAccessible(true);

        try {
            loadAllClassesInClassPath.invoke(myBeanLoader, classpath, result);
        } catch (Exception e) {
            assertTrue(e.getCause() instanceof RuntimeException);
        }
    }

    @Test
    void test_when_loadAllClassesInClassPath_is_called_the_Set_passed_as_input_is_populated_with_classes() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String classpath = "./target/classes";
        Set<Class<?>> result = new HashSet<>();
        Method loadAllClassesInClassPath = myBeanLoader.getClass().getDeclaredMethod("loadAllClassesInClassPath", String.class, Set.class);
        loadAllClassesInClassPath.setAccessible(true);

        loadAllClassesInClassPath.invoke(myBeanLoader, classpath, result);
        assertFalse(result.isEmpty());
    }

    @Test
    void test_when_loadBeans_is_called_that_populates_myBeanContainer_properly() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String classpath = "./target/classes";
        List<String> beans = new ArrayList<>();
        beans.add("Account");
        beans.add("Car");
        beans.add("DieselMotor");
        beans.add("PetrolMotor");

        myBeanLoader.loadBeans(classpath);

        assertEquals(4, myBeanContainer.getBeans().size());
        myBeanContainer.getBeans().forEach(bean -> assertTrue(beans.contains(bean.getClass().getSimpleName())));
    }
}