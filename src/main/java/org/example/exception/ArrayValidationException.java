package org.example.exception;

public class ArrayValidationException extends Exception {
    public ArrayValidationException(String message) {
        super(message);
    }

    public ArrayValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
