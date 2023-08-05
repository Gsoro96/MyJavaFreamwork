package org.example.container;

import java.util.ArrayList;
import java.util.List;

public class MyBeanContainer {

    List<Object> beans = new ArrayList<>();

    public void registerBean(Object bean){
        beans.add(bean);
    }

    public List<Object> getBeans(){
        return beans;
    }

    public Object getBean(Class<?> clazz){
        return beans.stream()
                .filter(obj -> obj.getClass().equals(clazz))
                .findFirst()
                .orElse(null);
    }
}
