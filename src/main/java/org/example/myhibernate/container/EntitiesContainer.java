package org.example.myhibernate.container;

import java.util.ArrayList;
import java.util.List;

public class EntitiesContainer {
    private List<Class<?>> entities = new ArrayList<>();

    public List<Class<?>> getEntities() {
        return entities;
    }

    public void registerEntity(Class<?> entity){ entities.add(entity);}

}
