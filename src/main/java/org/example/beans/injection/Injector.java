package org.example.beans.injection;

import org.example.annotations.MyInjection;
import org.example.beans.container.MyBeanContainer;

import java.util.Arrays;

import static org.example.utils.MyBeanUtils.fieldHasAnnotationOfType;


public class Injector {
    private final MyBeanContainer beanContainer;

    public Injector(MyBeanContainer beanContainer) {
        this.beanContainer = beanContainer;
    }

    public void performDependencyInjection() {
        beanContainer.getBeans().forEach(bean -> {
            Arrays.stream(bean.getClass().getDeclaredFields()).forEach(field -> {
                if (fieldHasAnnotationOfType(field,MyInjection.class)) {
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
