package com.example.Spring_boot_2.services.GlobalExceptions;
import com.example.Spring_boot_2.exceptions.OrderAlreadyExitsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderExists {
    @ExceptionHandler(OrderAlreadyExitsException.class)
    public String HandleUserException(OrderAlreadyExitsException exception) {
        String error = "An Order Already Exits With ID";
        return error;
    }
}

