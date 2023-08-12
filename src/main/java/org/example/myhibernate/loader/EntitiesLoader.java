package org.example.myhibernate.loader;

import org.apache.commons.io.FilenameUtils;

import org.example.annotations.MyEntity;
import org.example.myhibernate.container.EntitiesContainer;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.example.utils.MyBeanUtils.classHasAnnotationOfType;

public class EntitiesLoader {

    private final EntitiesContainer entitiesContainer;

    public EntitiesLoader(EntitiesContainer entitiesContainer) {
        this.entitiesContainer = entitiesContainer;
    }


    public void loadEntities(String classPath)  {
        Set<Class<?>> classes = loadAllClassesInClassPath(classPath);
        for (Class<?> clazz : classes) {
            if (classHasAnnotationOfType(clazz, MyEntity.class)) {
                entitiesContainer.registerEntity(clazz);
            }
        }
    }

    private Set<Class<?>> loadAllClassesInClassPath(String classPath) {
        Set<Class<?>> result = new HashSet<>();
        loadAllClassesInClassPath(classPath, result);
        return result;
    }

    private void loadAllClassesInClassPath(String classPath, Set<Class<?>> classes) {
        File pkgF = new File(classPath);

        if(pkgF == null || pkgF.listFiles() == null || pkgF.listFiles().length == 0){
            throw new RuntimeException(String.format("No such file as: %s",classPath));
        }

        for (File fileEntry : Objects.requireNonNull(pkgF.listFiles())) {
            if (fileEntry.isDirectory()) {
                loadAllClassesInClassPath(fileEntry.toString(), classes);
            } else {
                try {
                    String path = fileEntry.toString().substring(fileEntry.toString().indexOf("org"));
                    Class<?> clazz = Class.forName(FilenameUtils.removeExtension(path.replace('/', '.')));
                    classes.add(clazz);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
