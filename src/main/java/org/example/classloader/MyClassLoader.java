package org.example.classloader;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class MyClassLoader {

    private MyClassLoader() {
    }

    public static Set<Class<?>> loadAllClassesInClassPath(String classPath) {
        Set<Class<?>> result = new HashSet<>();
        loadAllClassesInClassPath(classPath, result);
        return result;
    }

    private static void loadAllClassesInClassPath(String classPath, Set<Class<?>> classes) {
        File pkgF = new File(classPath);

        if (pkgF == null || pkgF.listFiles() == null || pkgF.listFiles().length == 0) {
            throw new RuntimeException(String.format("No such file as: %s", classPath));
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
