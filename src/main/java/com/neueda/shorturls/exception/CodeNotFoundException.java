package com.neueda.shorturls.exception;

/**
 * Exception thrown when trying to retrieve an url
 * by a short code which does not exists in DB.
 */
public class CodeNotFoundException extends Exception {
    public CodeNotFoundException(String message) {
        super(message);
    }
}
