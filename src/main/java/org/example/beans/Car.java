package org.example.beans;

import org.example.annotations.MyBean;
import org.example.annotations.MyInjection;

@MyBean
public class Car implements Vehicle {
    @MyInjection
    private Motor motor;

    @Override
    public void makeSomeNoise() {
        System.out.println("Vroum vroum..!!!!");
        motor.display();
    }
}
