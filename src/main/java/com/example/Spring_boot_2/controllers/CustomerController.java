package com.example.Spring_boot_2.controllers;

import com.example.Spring_boot_2.dto.Customerdto;
import com.example.Spring_boot_2.entity.Customers;
import com.example.Spring_boot_2.services.impl.CustomerServiceimpl;
import com.example.Spring_boot_2.exceptions.NoCustomerExistException;
import com.example.Spring_boot_2.exceptions.columnAlreadyExistException;
import com.example.Spring_boot_2.exceptions.updateException;
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
    public List<String> getAllCustomerRegions() {
        return obj.getAllCusotmersRegions();
    }


    @GetMapping("/getAllCustomerInPages/{offset}/{pageSize}")
    public Page<Customers> getAllCustomerInPages(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        return obj.CustomerInPages(offset, pageSize);
    }

    @GetMapping("/getSearchCustomers/{offset}/{pageSize}/{text}")
    public Page<Customers> SearchedCustomer(@PathVariable Integer offset, @PathVariable Integer pageSize, @PathVariable String text) throws NoCustomerExistException {
        return obj.SearchedCustomer(offset, pageSize, text);
    }

    @PostMapping("/Post")
    public void saveCustomer(@RequestBody Customerdto customer) throws columnAlreadyExistException {
        Integer num = customer.getCustomerId();
        obj.SaveCustomer(customer, num);
    }


    @GetMapping("/getcustomer/{customerId}")
    public Optional<Customerdto> getcustomer(@PathVariable Integer customerId) throws NoCustomerExistException {
        return obj.getcustomer(customerId);
    }

    @PutMapping("/update")
    public void update(@RequestBody Customerdto customer) throws updateException {
        Integer num = customer.getCustomerId();
        obj.updateCustomer(customer, num);
    }

}


//    @Autowired
//    private Empservice empdetail;
//
//    @RequestMapping("/")
//    public List<Empdetails> hello(){
//        return empdetail.getAllEmp();
//    }
//    @RequestMapping("/{name}")
//    public Optional<Empdetails> getOne(@PathVariable String name){
//        return empdetail.getEmployee(name);
//    }
//    @RequestMapping(method = RequestMethod.POST,value = "/add")
//    public void add(@RequestBody Empdetails empdetails){
//        empdetail.addEmp(empdetails);
//    }
//
//    @RequestMapping(method = RequestMethod.PUT,value = "/put/{id}")
//    public void add(@RequestBody Empdetails empdetails,@PathVariable String name) {
//        empdetail.updateEmployees(name, empdetails);
//    }
//
//    @RequestMapping(method = RequestMethod.DELETE,value = "/delete/{id}")
//    public void delete(@RequestBody Empdetails empdetails,@PathVariable String name) {
//        empdetail.updateEmployees(name, empdetails);
//    }





