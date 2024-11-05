package com.example.m450.lb1.exception;

public class ResourceAlreadyExistsException extends ApplicationException {
    public ResourceAlreadyExistsException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s already exists with %s: '%s'", resourceName, fieldName, fieldValue));
    }
}