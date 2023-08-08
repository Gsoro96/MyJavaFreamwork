package org.example.beans;

import org.example.annotations.MyBean;
import org.example.annotations.MyPrimary;

@MyBean
@MyPrimary
public class DieselMotor implements Motor {
    @Override
    public void display() {
        System.out.println("Im am a Diesel Motor");
    }
}
