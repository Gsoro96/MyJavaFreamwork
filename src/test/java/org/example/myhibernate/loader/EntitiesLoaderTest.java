package org.example.myhibernate.loader;

import org.example.myhibernate.container.EntitiesContainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EntitiesLoaderTest {

    static EntitiesLoader entitiesLoader;
    static EntitiesContainer entitiesContainer;

    @BeforeAll
    static void setup() {
        entitiesContainer = new EntitiesContainer();
        entitiesLoader = new EntitiesLoader(entitiesContainer);
    }


    @Test
    void test_when_loadEntities_is_called_that_populates_entitiesContainer_properly() {
        String classpath = "./target/classes";
        List<String> entities = new ArrayList<>();
        entities.add("Account");

        entitiesLoader.loadEntities(classpath);

        assertEquals(entities.size(), entitiesContainer.getEntities().size());
        entitiesContainer.getEntities().forEach(entity -> assertTrue(entities.contains(entity.getSimpleName())));
    }
}