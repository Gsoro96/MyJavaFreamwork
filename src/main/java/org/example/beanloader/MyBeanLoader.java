package org.example.beanloader;

import org.example.annotations.MyBean;
import org.example.container.MyBeanContainer;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.io.FilenameUtils;

public class MyBeanLoader {
    private final MyBeanContainer beanContainer;

    public MyBeanLoader(MyBeanContainer beanContainer) {
        this.beanContainer = beanContainer;
    }

    public void loadBeans(String classPath) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Set<Class<?>> classes = loadAllClassesInClassPath(classPath);
        for (Class<?> clazz : classes) {
            if (Arrays.stream(clazz.getAnnotations()).anyMatch(MyBean.class::isInstance)) {
                beanContainer.registerBean(clazz.getConstructor().newInstance());
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
