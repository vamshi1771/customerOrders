package com.example.Spring_boot_2.exceptions;

public class updateException extends RuntimeException {

    private String Error;

    public updateException() {
    }

    public updateException(String error) {
        super(error);
        this.Error = error;
    }

}