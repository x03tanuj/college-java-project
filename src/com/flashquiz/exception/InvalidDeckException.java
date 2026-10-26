package com.flashquiz.exception;

// Unit 6: Checked Exception - must be declared in throws clause or caught with try/catch
public class InvalidDeckException extends Exception {

    public InvalidDeckException(String message) {
        super(message);
    }

    public InvalidDeckException(String message, Throwable cause) {
        super(message, cause);
    }
}
