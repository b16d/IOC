package com.acl.exception;

public class CyclicDependency extends RuntimeException {
    public CyclicDependency(String message) {
        super(message);
    }
}
