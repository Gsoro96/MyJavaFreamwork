package org.example.beans;

import org.example.beans.annotations.MyBean;
import org.example.beans.annotations.MyInjection;

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
