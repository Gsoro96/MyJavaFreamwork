package org.example.container;

import org.example.annotations.MyBean;
import org.example.annotations.MyPrimary;
import org.example.exceptions.MultipleBeansFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyBeanContainerTest {

    static MyBeanContainer myBeanContainer;

    @BeforeAll
    static void setup() {
        myBeanContainer = new MyBeanContainer();
    }

    @Test
    void test_when_registerBean_is_called_that_bean_is_registered() {
        String obj = "Test";
        myBeanContainer.registerBean(obj);

        assertEquals(obj, myBeanContainer.getBean(String.class));
    }

    @Test
    void test_when_getBeans_is_called_all_beans_are_returned() {
        String obj = "Test";
        myBeanContainer.registerBean(obj);

        assertEquals(1, myBeanContainer.getBeans().size());
        assertEquals(obj, myBeanContainer.getBeans().get(0));
    }


    interface TestInterface {

    }

    @MyBean
    class TestBean1 implements TestInterface {

    }

    @MyBean
    @MyPrimary
    class TestBean2 implements TestInterface {

    }

    @Test
    void test_when_getBean_is_called_and_multiple_beans_exist_of_same_type_returns_the_primary_bean() {
        TestInterface testbean1 = new TestBean1();
        TestInterface testbean2 = new TestBean2();
        myBeanContainer.registerBean(testbean1);
        myBeanContainer.registerBean(testbean2);

        assertEquals(testbean2, myBeanContainer.getBean(TestInterface.class));
    }

    @Test
    void test_when_isBeanOfDesiredType_is_called_checks_bean_is_of_type_class() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method isBeanOfDesiredType = myBeanContainer.getClass().getDeclaredMethod("isBeanOfDesiredType", Class.class, Object.class);
        isBeanOfDesiredType.setAccessible(true);

        String test = "Test";
        boolean invoke = (boolean) isBeanOfDesiredType.invoke(myBeanContainer, String.class, test);
        assertTrue(invoke);
    }

    @Test
    void test_when_isBeanOfDesiredType_is_called_checks_bean_implements_interface_of_type_class() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method isBeanOfDesiredType = myBeanContainer.getClass().getDeclaredMethod("isBeanOfDesiredType", Class.class, Object.class);
        isBeanOfDesiredType.setAccessible(true);

        List<String> testList = new ArrayList<>();
        assertTrue((Boolean) isBeanOfDesiredType.invoke(myBeanContainer, List.class, testList));
    }

    @Test
    void test_when_isPrimaryBean_is_called_on_a_bean_with_not_annotation_isPrimary_returns_false() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method isPrimaryBean = myBeanContainer.getClass().getDeclaredMethod("isPrimaryBean", Object.class);
        isPrimaryBean.setAccessible(true);

        String test = "test";
        assertFalse((Boolean) isPrimaryBean.invoke(myBeanContainer, test));
    }

    @Test
    void test_when_isPrimaryBean_is_called_on_a_bean_with_annotation_isPrimary_returns_true() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method isPrimaryBean = myBeanContainer.getClass().getDeclaredMethod("isPrimaryBean", Object.class);
        isPrimaryBean.setAccessible(true);

        TestBean2 test = new TestBean2();
        assertTrue((Boolean) isPrimaryBean.invoke(myBeanContainer, test));
    }

    @Test
    void test_when_getPrimaryBean_is_called_and_there_are_multiple_primary_beans_throw_MultipleBeansFoundException() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getPrimaryBean = myBeanContainer.getClass().getDeclaredMethod("getPrimaryBean", List.class);
        getPrimaryBean.setAccessible(true);

        List<Object> objects = new ArrayList<>();
        TestBean2 test1 = new TestBean2();
        TestBean2 test2 = new TestBean2();
        objects.add(test1);
        objects.add(test2);

        try {
            getPrimaryBean.invoke(myBeanContainer, objects);
        }catch (Exception e){
            assertTrue(e.getCause() instanceof MultipleBeansFoundException);
        }
    }
}