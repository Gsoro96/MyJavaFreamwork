package org.example.beans;

import org.example.beans.annotations.MyBean;
import org.example.beans.annotations.MyPrimary;

@MyBean
@MyPrimary
public class DieselMotor implements Motor {
    @Override
    public void display() {
        System.out.println("Im am a Diesel Motor");
    }
}
