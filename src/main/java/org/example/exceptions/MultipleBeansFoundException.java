package org.example.exceptions;

public class MultipleBeansFoundException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "Multiple beans found for the provided class consider using @MyPrimary Annotation";

    public MultipleBeansFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
