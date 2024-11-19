package com.example.Spring_boot_2.services.impl;

import com.example.Spring_boot_2.dto.*;
import com.example.Spring_boot_2.entity.Customers;
import com.example.Spring_boot_2.entity.Products;
import com.example.Spring_boot_2.repository.CustomerRepository;
import com.example.Spring_boot_2.repository.OrderRepository;
import com.example.Spring_boot_2.repository.ProductsRepository;
import com.example.Spring_boot_2.services.CustomerService;
import com.example.Spring_boot_2.exceptions.NoCustomerExistException;
import com.example.Spring_boot_2.exceptions.updateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerServiceimpl implements CustomerService {
    @Autowired
    private CustomerRepository CustRepo;
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private ProductsRepository productsRepository;

    public CustomerServiceimpl() {
    }

    public List<Customerdto> getAllCusotmers(){
       List<Customers> customer= CustRepo.findAll().stream().collect(Collectors.toList());
        System.out.println("customer"+customer);
        List<Customerdto> customersList = customer.stream()
                .map(originalObject -> convertintoDto(originalObject)) // Map each object to a new object
                .collect(Collectors.toList());

        return customersList;
    }

    public regionsDto getAllCusotmersRegions(){
        regionsDto regionsDto = new regionsDto();
        List<String> regions = CustRepo.getCustomerregions()
                .stream()
                .collect(Collectors.toList());
        regionsDto.setRegions(regions);
        Integer customerCount =  CustRepo.findAll().size();
        regionsDto.setCustomersCount(customerCount);
        Integer orderCount = orderRepo.findAll().size();
        regionsDto.setOrdersCount(orderCount);
        Integer ProductCount = productsRepository.findAll().size();
        regionsDto.setProductsCount(ProductCount);
        Integer outOfStock = productsRepository.findOutOfStock();
        regionsDto.setOutOfStock(outOfStock);
        return regionsDto;
    }

    @Override
    public  CustomersList CustomerInPages(int offset, int pageSize) {
        int startIndex = (offset == 0) ? 0 : offset*pageSize;
       List<Object[]> results =  CustRepo.getAllCustomerInPages(startIndex,pageSize);
       List<Customers> customers = CustRepo.findAll();
       int customersCont = customers.size();
        customersPageableDto customerDto = new customersPageableDto();
        List<customersPageableDto> customersList = results.stream()
                .map(originalObject -> convertIntoCustomerPageableDto(originalObject,pageSize))
                .collect(Collectors.toList());
        CustomersList customersList1 = new CustomersList();
        customersList1.setCustomersPageable(customersList);
        int pageCount = (int) Math.ceil((double) customersCont/pageSize);
        System.out.println("pageCount"+pageCount);
        customersList1.setPageCount((long) pageCount);
        customersList1.setPageIndex((long) offset+1);
        return customersList1;
    }

    @Override
    public void SaveCustomer(Customerdto customers,String name) throws NoCustomerExistException {
        Customers customers1 = new Customers();
        customers1= convertIntoCusotmerFromCustormerdto(customers);
        String customerName = name.trim();
        List<String> cust = CustRepo.findByCustomerName(customerName) ;
        if(cust.size() > 0){
            throw  new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Same Customer Name Already Exits");
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
                         customers.getPhoneNumber(),
                         customers.getAddress(),
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
        customers.setCustomername(customer.getCustomerName());
        customers.setPhoneNumber(customer.getPhoneNumber());
        customers.setRegion(customer.getRegion());
        customers.setGender(customer.getGender());
        customers.setAddress(customer.getAddress());
        customers.setCustomerid(customers.getCustomerid());
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
        customerdto.setPhoneNumber(customer.getPhoneNumber());
        customerdto.setCustomerName(customer.getCustomername());
        customerdto.setRegion(customer.getRegion());
        customerdto.setGender(customer.getGender());
        customerdto.setAddress(customer.getAddress());
        return customerdto;
    }

    private customersPageableDto convertIntoCustomerPageableDto(Object[] obj,Integer pagesize ){
        customersPageableDto customersPageableDto = new customersPageableDto();
        customersPageableDto.setCustomerName(obj[1].toString());
        customersPageableDto.setRegion(obj[2].toString());
        customersPageableDto.setGender(obj[3].toString());
        BigInteger bigInteger = (BigInteger) obj[4];
        customersPageableDto.setOrderCount(bigInteger.longValue());
        customersPageableDto.setCustomerId((Integer) obj[0]);
        return customersPageableDto;
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

    public CustomersAndProductsDto getCustomersAndProducts() {
        List<Customers> customersList  = CustRepo.findAll();
        Customerdto customerObj = new Customerdto();
        List<Customerdto> customersLists = customersList.stream()
                .map(originalObject -> convertintoDto(originalObject))
                .collect(Collectors.toList());
        List<Products> productsList = productsRepository.findAll();
        CustomersAndProductsDto customersAndProductsDto =new CustomersAndProductsDto();
        customersAndProductsDto.setCustomersList(customersLists);
        customersAndProductsDto.setProductLists(productsList);

        return  customersAndProductsDto;
    }
}




