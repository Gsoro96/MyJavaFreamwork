package org.example.classloader;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MyClassLoaderTest {

    @Test
    void test_when_loadAllClassesInClassPath_is_called_with_invalid_classpath_RuntimeException_is_thrown() {
        assertThrows(RuntimeException.class, () -> MyClassLoader.loadAllClassesInClassPath(""));
    }

    @Test
    void test_when_loadAllClassesInClassPath_is_called_the_Set_passed_as_input_is_populated_with_classes() {
        String classpath = "./target/classes";
        Set<Class<?>> result = MyClassLoader.loadAllClassesInClassPath(classpath);
        assertFalse(result.isEmpty());
    }
}