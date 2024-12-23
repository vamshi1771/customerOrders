package com.example.Spring_boot_2.services;

import com.example.Spring_boot_2.dto.*;
import com.example.Spring_boot_2.entity.Customers;
import com.example.Spring_boot_2.exceptions.NoCustomerExistException;
import com.example.Spring_boot_2.exceptions.columnAlreadyExistException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<Customerdto> getAllCustomers();
    public dashboardDto getAllCustomersRegions();

    public CustomersList CustomerInPages(int offset, int pagesize);

    public Page<Customers> SearchedCustomer(Integer offset,Integer pagesize,String text) throws NoCustomerExistException;

    void updateUser(UserDto userDto,String name) throws columnAlreadyExistException;

    public CustomersAndProductsDto getCustomersAndProducts();
    Optional<Customerdto> getCustomer(Long id)throws NoCustomerExistException;
}
