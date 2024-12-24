package com.example.Spring_boot_2.exceptions;

public class NoCustomerExistException extends RuntimeException {
    private String error;
    public NoCustomerExistException() {}
    public NoCustomerExistException(String error) {
        super(error);
        this.error = error;
    }
}
