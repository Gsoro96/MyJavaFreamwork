package org.example.injection;

import org.example.beans.annotations.MyInjection;
import org.example.container.MyBeanContainer;

import java.util.Arrays;


public class Injector {
    private final MyBeanContainer beanContainer;

    public Injector(MyBeanContainer beanContainer) {
        this.beanContainer = beanContainer;
    }

    public void performDependencyInjection() {
        beanContainer.getBeans().forEach(bean -> {
            Arrays.stream(bean.getClass().getDeclaredFields()).forEach(field -> {
                if (Arrays.stream(field.getAnnotations()).anyMatch(annotation -> annotation instanceof MyInjection)) {
                    Object objToBeInjected = beanContainer.getBean(field.getType());
                    field.setAccessible(true);
                    try {
                        field.set(bean,objToBeInjected);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        });
    }
}
