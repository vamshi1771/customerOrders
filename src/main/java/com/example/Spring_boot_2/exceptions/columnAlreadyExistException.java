package com.example.Spring_boot_2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class columnAlreadyExistException extends RuntimeException {
    private String Error;

    public columnAlreadyExistException() {
    }

    public columnAlreadyExistException(String error) {
        super(error);
        this.Error = error;
    }
}
