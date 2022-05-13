package ru.borisof.navicampus.core.common.exception;

public class ValidationException extends RuntimeException{
    public ValidationException(final String message) {
        super(message);
    }
}
