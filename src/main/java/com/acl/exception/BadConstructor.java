package com.acl.exception;

public class BadConstructor extends RuntimeException {

    public BadConstructor(String message) {
        super(message);
    }
}
