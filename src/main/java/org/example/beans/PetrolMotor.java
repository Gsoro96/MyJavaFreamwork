package org.example.beans;

import org.example.beans.annotations.MyBean;

@MyBean
public class PetrolMotor implements Motor {
    @Override
    public void display() {
        System.out.println("Im am a Petrol Motor");
    }
}
