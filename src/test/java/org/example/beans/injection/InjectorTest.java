package org.example.beans.injection;

import org.example.annotations.MyBean;
import org.example.annotations.MyInjection;
import org.example.beans.container.MyBeanContainer;
import org.example.beans.injection.Injector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class InjectorTest {

    static Injector injector;
    static MyBeanContainer myBeanContainer;

    @BeforeAll
    static void setup() {
        myBeanContainer = new MyBeanContainer();
        injector = new Injector(myBeanContainer);
    }

    @MyBean
    static class TestBean {
        @MyInjection
        private TestInterface testField;

        public TestInterface getTestField() {
            return testField;
        }
    }

    interface TestInterface {
    }

    @MyBean
    static class TestImplementation implements TestInterface {
    }

    @Test
    void test_performDependencyInjection() {
        TestBean testBean = new TestBean();
        TestInterface testInterface = new TestImplementation();

        myBeanContainer.registerBean(testBean);
        myBeanContainer.registerBean(testInterface);
        injector.performDependencyInjection();

        Assertions.assertNotNull(testBean.getTestField());
        Assertions.assertTrue(testBean.getTestField() instanceof TestImplementation);
    }
}