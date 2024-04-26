package com.example.Spring_boot_2.services;

import com.example.Spring_boot_2.dto.Customerdto;
import com.example.Spring_boot_2.entity.Customers;
import com.example.Spring_boot_2.exceptions.NoCustomerExistException;
import com.example.Spring_boot_2.exceptions.columnAlreadyExistException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<Customerdto> getAllCusotmers();
    public List<String> getAllCusotmersRegions();

    public Page<Customers> CustomerInPages(int offset, int pagesize);

    public Page<Customers> SearchedCustomer(Integer offset,Integer pagesize,String text) throws NoCustomerExistException;

    void SaveCustomer(Customerdto  customers,Integer num) throws columnAlreadyExistException;

    Optional<Customerdto> getcustomer(Integer id)throws NoCustomerExistException;
}
