package com.example.Spring_boot_2.services.GlobalExceptions;


import com.example.Spring_boot_2.exceptions.updateException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductsException {

    @ExceptionHandler(updateException.class)
    public String handeUserException(updateException exception){
            String error = exception.getMessage().toString();
            return error;
    }
}
