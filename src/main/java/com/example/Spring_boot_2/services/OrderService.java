package com.example.Spring_boot_2.services;

import com.example.Spring_boot_2.dto.Orderdto;
import com.example.Spring_boot_2.dto.orderList;
import com.example.Spring_boot_2.dto.pageableOrders;
import com.example.Spring_boot_2.dto.regionsDto;
import com.example.Spring_boot_2.entity.Orders;
import com.example.Spring_boot_2.exceptions.NoCustomerExistException;
import com.example.Spring_boot_2.exceptions.NoOrderExistsException;
import com.example.Spring_boot_2.exceptions.NoOrdersForThisCustomer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Optional;

public interface OrderService {


    public ResponseEntity<String> SaveOrder(Orderdto order);
    public List<Orderdto> getOrder(Integer id) throws NoOrderExistsException;

    public List<Orders> getAllOrders();
    public List<Orderdto> getByRegion(String Name);
    public List<Orderdto> getOrdersByCustomer_Id(Integer id)throws NoOrdersForThisCustomer;
    public orderList OrdersInPages(Integer offset, Integer pageSize);
    public regionsDto getAllProducts();
    @RestControllerAdvice
    class UserNotFindException {


        @ExceptionHandler(NoCustomerExistException.class)
        public String HandleUserException(NoCustomerExistException exception){
            String  error="No Such Customer Exists";
            return error;
        }
    }
}
