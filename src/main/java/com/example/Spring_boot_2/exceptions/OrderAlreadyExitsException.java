package com.example.Spring_boot_2.exceptions;

public class OrderAlreadyExitsException extends RuntimeException {
    private String Error;
    public OrderAlreadyExitsException() {
    }
    public OrderAlreadyExitsException(String error) {
        super(error);
        this.Error = error;
    }
}