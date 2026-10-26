package com.flashquiz.exception;

// Unit 6: Unchecked Exception (Runtime) - indicates programming or runtime input errors
public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
