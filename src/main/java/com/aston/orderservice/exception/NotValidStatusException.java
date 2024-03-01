package com.aston.orderservice.exception;

public class NotValidStatusException extends Exception {
    public NotValidStatusException(String message) {
        super(message);
    }
}
