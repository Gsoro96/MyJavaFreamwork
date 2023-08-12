package org.example.utils;

import org.example.annotations.MyBean;
import org.example.annotations.MyInjection;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.example.utils.MyBeanUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class MyBeanUtilsTest {

    @MyBean
    static class TestClass1{

        @MyInjection
        private String testField;
    }


    @Test
    void test_objectHasAnnotationOfType(){
        TestClass1 testClass = new TestClass1();
        assertTrue(objectHasAnnotationOfType(testClass,MyBean.class));
    }

    @Test
    void test_fieldHasAnnotationOfType() throws NoSuchFieldException {
        TestClass1 testClass = new TestClass1();
        Field testClass2 = testClass.getClass().getDeclaredField("testField");

        assertTrue(fieldHasAnnotationOfType(testClass2,MyInjection.class));
    }

    @Test
    void test_classHasAnnotationOfType(){
        assertTrue(classHasAnnotationOfType(TestClass1.class,MyBean.class));
    }

}