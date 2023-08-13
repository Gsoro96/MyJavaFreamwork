package org.example.myhibernate.loader;

import org.example.annotations.MyEntity;
import org.example.classloader.MyClassLoader;
import org.example.myhibernate.container.EntitiesContainer;

import java.util.Set;

import static org.example.utils.MyBeanUtils.classHasAnnotationOfType;

public class EntitiesLoader {

    private final EntitiesContainer entitiesContainer;

    public EntitiesLoader(EntitiesContainer entitiesContainer) {
        this.entitiesContainer = entitiesContainer;
    }

    public void loadEntities(String classPath) {
        Set<Class<?>> classes = MyClassLoader.loadAllClassesInClassPath(classPath);
        for (Class<?> clazz : classes) {
            if (classHasAnnotationOfType(clazz, MyEntity.class)) {
                entitiesContainer.registerEntity(clazz);
            }
        }
    }

}
