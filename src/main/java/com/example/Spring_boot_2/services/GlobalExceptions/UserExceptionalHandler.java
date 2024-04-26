package com.example.Spring_boot_2.services.GlobalExceptions;

import com.example.Spring_boot_2.exceptions.NoCustomerExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionalHandler {
    @ExceptionHandler(NoCustomerExistException.class)
    public ResponseEntity<String> HandleUserException(NoCustomerExistException exception) {
        String error = exception.getMessage().toString();
        System.out.println(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_GATEWAY);
      }
    }

