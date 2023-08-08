package org.example.exceptions;

public class EntityMisuseException extends  RuntimeException{
    public EntityMisuseException(String message) {
        super(message);
    }
}
