package com.example.Spring_boot_2.services.GlobalExceptions;

import com.example.Spring_boot_2.exceptions.NoOrderExistsException;
import com.example.Spring_boot_2.exceptions.NoOrdersForThisCustomer;
import com.example.Spring_boot_2.exceptions.OrderAlreadyExitsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrdersException {
    @ExceptionHandler(NoOrderExistsException.class)
    public String HandleUserException(NoOrderExistsException exception) {
        String error = "No Such Order Exists";
        return error;
    }

    @ExceptionHandler(OrderAlreadyExitsException.class)
    public String HandleUserException(OrderAlreadyExitsException exception) {
        String error = "An Order Already Exits With ID";
        return error;
    }

    @ExceptionHandler(NoOrdersForThisCustomer.class)
    public String HandleUserException(NoOrdersForThisCustomer exception) {
        String error = "No Orders With This Customer ID";
        return error;
    }
}

