package com.example.Spring_boot_2.controllers;

import com.example.Spring_boot_2.dto.Customerdto;
import com.example.Spring_boot_2.dto.CustomersList;
import com.example.Spring_boot_2.dto.customersPageableDto;
import com.example.Spring_boot_2.dto.regionsDto;
import com.example.Spring_boot_2.entity.Customers;
import com.example.Spring_boot_2.services.impl.CustomerServiceimpl;
import com.example.Spring_boot_2.exceptions.NoCustomerExistException;
import com.example.Spring_boot_2.exceptions.columnAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class CustomerController {
    @Autowired
    CustomerServiceimpl obj;


    @GetMapping("/getAllCustomers")
    public List<Customerdto> getAllCustomer() {
        return obj.getAllCusotmers();
    }

    @GetMapping("/getAllCustomersRegions")
    public regionsDto getAllCustomerRegions() {
        return obj.getAllCusotmersRegions();
    }


    @GetMapping("/getAllCustomerInPages/{offset}/{pageSize}")
    public CustomersList getAllCustomerInPages(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        return obj.CustomerInPages(offset, pageSize);
    }

    @GetMapping("/getSearchCustomers/{offset}/{pageSize}/{text}")
    public Page<Customers> SearchedCustomer(@PathVariable Integer offset, @PathVariable Integer pageSize, @PathVariable String text) throws NoCustomerExistException {
        return obj.SearchedCustomer(offset, pageSize, text);
    }

    @PostMapping("/Post")
    public void saveCustomer(@RequestBody Customerdto customer) throws columnAlreadyExistException {
        String name = customer.getCustomerName();
        obj.SaveCustomer(customer, name);
    }


    @GetMapping("/getcustomer/{customerId}")
    public Optional<Customerdto> getcustomer(@PathVariable Integer customerId) throws NoCustomerExistException {
        return obj.getcustomer(customerId);
    }
}








