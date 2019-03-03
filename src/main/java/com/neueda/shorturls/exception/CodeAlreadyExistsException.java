package com.neueda.shorturls.exception;

/**
 * Exception is thrown when trying to save a short url with the
 * same code but different long url. Handled by the application in order
 * to generate a different code.
 */
public class CodeAlreadyExistsException extends Exception {
    public CodeAlreadyExistsException(String message) {
        super(message);
    }
}
