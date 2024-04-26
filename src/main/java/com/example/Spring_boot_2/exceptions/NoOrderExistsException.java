package com.example.Spring_boot_2.exceptions;

public class NoOrderExistsException extends Exception {
    private String error;

    public NoOrderExistsException() {
    }

    public NoOrderExistsException(String error) {
        super(error);
        this.error = error;
    }
}