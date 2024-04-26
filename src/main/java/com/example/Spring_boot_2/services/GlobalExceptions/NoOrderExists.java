package com.example.Spring_boot_2.services.GlobalExceptions;

import com.example.Spring_boot_2.exceptions.NoOrderExistsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NoOrderExists {
    @ExceptionHandler(NoOrderExistsException.class)
    public String HandleUserException(NoOrderExistsException exception) {
        String error = "No Such Order Exists";
        return error;
    }
}

