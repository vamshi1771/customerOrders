package com.example.Spring_boot_2.controllers;

import com.example.Spring_boot_2.dto.Customerdto;
import com.example.Spring_boot_2.dto.CustomersList;
import com.example.Spring_boot_2.dto.UserDto;
import com.example.Spring_boot_2.dto.dashboardDto;
import com.example.Spring_boot_2.entity.Customers;
import com.example.Spring_boot_2.exceptions.NoCustomerExistException;
import com.example.Spring_boot_2.exceptions.columnAlreadyExistException;
import com.example.Spring_boot_2.services.impl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class CustomerController {
    @Autowired
    CustomerServiceImpl customerService;


    @GetMapping("/getAllCustomers")
    public List<Customerdto> getAllCustomer() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/getAllCustomersRegions")
    public dashboardDto getAllCustomerRegions() {
        return customerService.getAllCustomersRegions();
    }


    @GetMapping("/getAllCustomerInPages/{offset}/{pageSize}")
    public CustomersList getApo0llCustomerInPages(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        return customerService.CustomerInPages(offset, pageSize);
    }

    @GetMapping("/getSearchCustomers/{offset}/{pageSize}/{text}")
    public Page<Customers> SearchedCustomer(@PathVariable Integer offset, @PathVariable Integer pageSize, @PathVariable String text) throws NoCustomerExistException {
        return customerService.SearchedCustomer(offset, pageSize, text);
    }

    @PostMapping("/updateUser")
    public void saveCustomer(@RequestBody UserDto userDto) throws columnAlreadyExistException {
        String name = userDto.getUserName();
        customerService.updateUser(userDto, name);
    }

    @GetMapping("/getCustomer/{userId}")
    public Optional<Customerdto> getCustomer(@PathVariable Integer userId) throws NoCustomerExistException {
        return customerService.getCustomer(Long.valueOf(userId));
    }


}








