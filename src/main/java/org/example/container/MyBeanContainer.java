package org.example.container;

import org.example.annotations.MyPrimary;
import org.example.exceptions.MultipleBeansFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MyBeanContainer {

    List<Object> beans = new ArrayList<>();

    public void registerBean(Object bean) {
        beans.add(bean);
    }

    public List<Object> getBeans() {
        return beans;
    }

    public Object getBean(Class<?> clazz) {
        List<Object> beansFound = beans.stream()
                .filter(obj -> isBeanOfDesiredType(clazz, obj))
                .collect(Collectors.toList());

        if(beansFound.isEmpty()){
            return null;
        } else if (beansFound.size() == 1) {
            return beansFound.get(0);
        }else{
            return getPrimaryBean(beansFound);
        }
    }

    private static boolean isBeanOfDesiredType(Class<?> clazz, Object obj) {
        return obj.getClass().equals(clazz) || Arrays.asList(obj.getClass().getInterfaces()).contains(clazz);
    }

    private Object getPrimaryBean(List<Object> beans) {
        List<Object> primaryBeans = beans.stream()
                .filter(this::isPrimaryBean)
                .collect(Collectors.toList());
        if(primaryBeans.isEmpty() || primaryBeans.size() != 1){
            throw new MultipleBeansFoundException();
        }else {
            return primaryBeans.get(0);
        }
    }

    private boolean isPrimaryBean(Object bean) {
        return Arrays.stream(bean.getClass().getAnnotations()).anyMatch(annotation -> annotation instanceof MyPrimary);
    }
}
