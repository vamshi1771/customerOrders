package com.example.Spring_boot_2.services;

import com.example.Spring_boot_2.entity.Orders;
import com.example.Spring_boot_2.exceptions.NoCustomerExistException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Optional;

public interface OrderService {


    void SaveOrder(Orders order);
    void Updateorder(String o_name,Integer id);
    Optional<Orders> getcustomer(Integer id);

    List<String> getByregion(String Name);
    List<Orders> getordersByCustomer_Id(String Name);

    @RestControllerAdvice
    class UserNotFindException {


        @ExceptionHandler(NoCustomerExistException.class)
        public String HandleUserException(NoCustomerExistException exception){
            String  error="No Such Customer Exists";
            return error;
        }
    }
}
