package com.example.Spring_boot_2.services.GlobalExceptions;

import com.example.Spring_boot_2.exceptions.NoOrdersForThisCustomer;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NoOrdersForCustomer {
    @ExceptionHandler(NoOrdersForThisCustomer.class)
    public String HandleUserException(NoOrdersForThisCustomer exception) {
        String error = "No Orders With This Customer ID";
        return error;
    }
}
