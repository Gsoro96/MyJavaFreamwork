package org.example.beans;

import org.example.beans.annotations.MyBean;

@MyBean
public class Car implements Vehicle {
    @Override
    public void makeSomeNoise() {
        System.out.println("Vroum vroum..!!!!");
    }
}
