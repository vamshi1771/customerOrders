package com.example.Spring_boot_2.services.GlobalExceptions;

import com.example.Spring_boot_2.exceptions.columnAlreadyExistException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class userInputException {


    @ExceptionHandler(columnAlreadyExistException.class)
    public String HandleException(columnAlreadyExistException exception){
        String  error="Customer With This ID Already Exits";
        return error;
    }
}
