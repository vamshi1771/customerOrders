package com.example.Spring_boot_2.services.impl;

import com.example.Spring_boot_2.dto.Customerdto;
import com.example.Spring_boot_2.dto.Paginationdto;
import com.example.Spring_boot_2.entity.Customers;
import com.example.Spring_boot_2.repository.CustomerRepository;
import com.example.Spring_boot_2.repository.OrderRepository;
import com.example.Spring_boot_2.services.CustomerService;
import com.example.Spring_boot_2.exceptions.NoCustomerExistException;
import com.example.Spring_boot_2.exceptions.columnAlreadyExistException;
import com.example.Spring_boot_2.exceptions.updateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerServiceimpl implements CustomerService {
    @Autowired
    private CustomerRepository CustRepo;
    @Autowired
    private OrderRepository orderRepo;

    public CustomerServiceimpl() {
    }

    public List<Customerdto> getAllCusotmers(){
       List<Customers> customer= CustRepo.findAll().stream().collect(Collectors.toList());
       List<Customerdto> CustDto = null;
        for (Customers convertintoDto:customer) {
            CustDto.add(convertintoDto(convertintoDto));
        }
        return CustDto;
    }

    public List<String> getAllCusotmersRegions(){
        return CustRepo.getCustomerregions()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public Page<Customers> CustomerInPages(int offset, int pagesize) {
        return CustRepo.findAll(PageRequest.of(offset,pagesize));
    }

    @Override
    public void SaveCustomer(Customerdto customers,Integer num) throws columnAlreadyExistException {
        Customers customers1=new Customers();
        customers1= convertIntoCusotmerFromCustormerdto(customers);
        Optional<Customers> cust = CustRepo.findById(num);
        if(cust.isPresent()){
            throw new columnAlreadyExistException("Customer Already Exists");
        }
        else {
            CustRepo.save(customers1);
        }
    }
    public Optional<Customerdto> getcustomer(Integer id) throws NoCustomerExistException {
        if(!CustRepo.findById(id).isPresent()){
            throw new NoCustomerExistException("No Such Customer Exits");
        }
        else {
         return   CustRepo.findById(id)
                 .map(customers -> new Customerdto(
                         customers.getCustomerid(),
                         customers.getCustomername(),
                         customers.getRegion(),
                         customers.getGender()));
        }
    }


    public void updateCustomer(Customerdto customers,Integer num) throws updateException {
        Customers customers1=new Customers();
        customers1= convertIntoCusotmerFromCustormerdto(customers);
        Optional<Customers> cust = CustRepo.findById(num);

        if(cust.isPresent()){
            throw new updateException("No Such Customter To Update");
        }
        else {
            CustRepo.save(customers1);
        }
    }




    


    private Paginationdto convertIntoPagination(Customers customer){
        Paginationdto paginationdto=new Paginationdto();
        paginationdto.setCustomerId(customer.getCustomerid());
        paginationdto.setCustomerName(customer.getCustomername());
        paginationdto.setRegion(customer.getRegion());
        paginationdto.setGender(customer.getGender());

        return paginationdto;
    }




    private Customers convertIntoCusotmerFromCustormerdto(Customerdto customer){
        Customers customers = new Customers();
        customers.setCustomerid(customer.getCustomerId());
        customers.setCustomername(customer.getCustomerName());
        customers.setRegion(customer.getRegion());
        customers.setGender(customer.getGender());


        return customers;
    }


    private Customers convertIntoCustomersFromPagination(Paginationdto customer){
        Customers customers = new Customers();
        customers.setCustomerid(customer.getCustomerId());
        customers.setCustomername(customer.getCustomerName());
        customers.setRegion(customer.getRegion());
        customers.setGender(customer.getGender());


        return customers;
    }




    public List<Customers> CustomerInPages(int offset, int pagesize,String text) {

        return  CustRepo.findAll(PageRequest.of(offset,pagesize))
                .getContent()
                .stream()
                .collect(Collectors.toList());
    }




    private Customerdto convertintoDto(Customers customer){
        Customerdto customerdto = new Customerdto();
        customerdto.setCustomerId(customer.getCustomerid());
        customerdto.setCustomerName(customer.getCustomername());
        customerdto.setRegion(customer.getRegion());
        customerdto.setGender(customer.getGender());
        return customerdto;
    }
    
    public Page<Customers> SearchedCustomer(Integer offset,Integer pagesize,String text) throws NoCustomerExistException {


        String[] newWord=text.split(" ");
        String querySearch="%";

        for(String word:newWord){
            querySearch+=word;
            querySearch+='%';
        }

        Page<Object[]> obj =  CustRepo.searchcustomer(querySearch,PageRequest.of(offset,pagesize));
        if(obj.getTotalElements()==0) {
            System.out.println("custom Exception");
            throw new NoCustomerExistException("Customer Not Present");
        }
        Page<Customers> pg = obj.map(item -> {
            Customers cst = new Customers();
            cst.setCustomerid((Integer) item[0]);
            cst.setCustomername((String) item[1]);
            cst.setGender((String) item[2]);
            cst.setRegion((String) item[3]);
            System.out.println(cst);
            return cst;
        });
        return pg;
    }
}



