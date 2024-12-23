package com.example.Spring_boot_2.services.impl;

import com.example.Spring_boot_2.dto.*;
import com.example.Spring_boot_2.entity.Customers;
import com.example.Spring_boot_2.entity.Products;
import com.example.Spring_boot_2.entity.User;
import com.example.Spring_boot_2.repository.CustomerRepository;
import com.example.Spring_boot_2.repository.OrderRepository;
import com.example.Spring_boot_2.repository.ProductsRepository;
import com.example.Spring_boot_2.repository.UserRepository;
import com.example.Spring_boot_2.services.CustomerService;
import com.example.Spring_boot_2.exceptions.NoCustomerExistException;
import com.example.Spring_boot_2.exceptions.updateException;
import org.apache.poi.ss.formula.functions.Na;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository CustRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private ProductsRepository productsRepository;

    public CustomerServiceImpl() {
    }

    public List<Customerdto> getAllCustomers(){
        List<User> users = userRepository.findByRole("CUSTOMER").stream().collect(Collectors.toList());
        List<Customerdto> customerDto = users.stream()
                .map(this::convertIntoCustomerDto)
                .collect(Collectors.toList());
        return customerDto;
    }
    private Customerdto convertIntoCustomerDto(User customer) {
        Customerdto customerDto = new Customerdto();
        customerDto.setCustomerId(customer.getUser_id());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        customerDto.setCustomerName(customer.getUserName());
        customerDto.setRegion(customer.getRegion());
        customerDto.setGender(customer.getGender());
        customerDto.setAddress(customer.getAddress());
        return customerDto;
    }



    public dashboardDto getAllCustomersRegions(){
        dashboardDto regionsDto = new dashboardDto();
        List<String> regions = new ArrayList<>(userRepository.getCustomerRegions());
        regionsDto.setRegions(regions);
        Long customerCount = userRepository.getTotalCustomers();
        regionsDto.setCustomersCount(customerCount);
        Integer orderCount = orderRepo.findAll().size();
        regionsDto.setOrdersCount(orderCount);
        Integer productCount = productsRepository.findAll().size();
        regionsDto.setProductsCount(productCount);
        Integer outOfStock = productsRepository.findOutOfStock();
        regionsDto.setOutOfStock(outOfStock);
        return regionsDto;
    }

    @Override
    public  CustomersList CustomerInPages(int offset, int pageSize) {
        int startIndex = (offset == 0) ? 0 : offset*pageSize;
       List<Object[]> results =  userRepository.getAllCustomerInPages(startIndex,pageSize);
        System.out.println(results);
        BigInteger customersCount = BigInteger.valueOf(userRepository.getTotalCustomers());
        customersPageableDto customerDto = new customersPageableDto();
        List<customersPageableDto> customersList = results.stream()
                .map(originalObject -> convertIntoCustomerPageableDto(originalObject,pageSize))
                .collect(Collectors.toList());
        CustomersList customersList1 = new CustomersList();
        customersList1.setCustomersPageable(customersList);
        int pageCount = (int) Math.ceil((double) customersCount.longValue()/pageSize);
        customersList1.setPageCount((long) pageCount);
        customersList1.setPageIndex((long) offset+1);
        return customersList1;
    }
    private customersPageableDto convertIntoCustomerPageableDto(Object[] obj,Integer pageSize ){
        customersPageableDto customersPageableDto = new customersPageableDto();
        customersPageableDto.setCustomerName(obj[1].toString());
        customersPageableDto.setRegion(obj[2].toString());
        customersPageableDto.setGender(obj[3].toString());
        if (obj[4] instanceof BigInteger) {
            BigInteger bigInteger = (BigInteger) obj[4];
            customersPageableDto.setOrderCount(bigInteger.longValue());
        } else if (obj[4] instanceof Long) {
            customersPageableDto.setOrderCount((Long) obj[4]);
        }
        customersPageableDto.setCustomerId(((Number) obj[0]).longValue());
        return customersPageableDto;
    }

    @Override
    public void updateUser(UserDto userDto,String name) {

        User user = UserMapper.toUser(userDto);
        String Name = name.trim();
        User existingUser = userRepository.findByUserName(Name);
        String customerName = existingUser.getUserName();
            if (userDto.getUserName() != null) {
                existingUser.setUserName(userDto.getUserName());
            }
            if (userDto.getEmail() != null) {
                existingUser.setEmail(userDto.getEmail());
            }
            if (userDto.getRegion() != null) {
                existingUser.setRegion(userDto.getRegion());
            }
            if (userDto.getGender() != null) {
                existingUser.setGender(userDto.getGender());
            }
            if (userDto.getPhoneNumber() != null) {
                existingUser.setPhoneNumber(userDto.getPhoneNumber());
            }
            if (userDto.getAddress() != null) {
                existingUser.setAddress(userDto.getAddress());

            userRepository.save(existingUser);
        }
    }


    public Optional<Customerdto> getCustomer(Long id) throws NoCustomerExistException {
        if(!userRepository.findById(id).isPresent()){
            throw new NoCustomerExistException("No Such Customer Exits");
        }
        else {
         return   userRepository.findById(id)
                 .map(customers -> new Customerdto(
                         customers.getUser_id(),
                         customers.getPhoneNumber(),
                         customers.getAddress(),
                         customers.getUserName(),
                         customers.getRegion(),
                         customers.getGender()));
        }
    }

// input updated from CustomerDto to UserDTO
    public void updateCustomer(UserDto userDto,Long userId) throws updateException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new updateException("Customer not found with ID: " + userId);
        }
        User user = optionalUser.get();
        if (userDto.getUserName() != null) user.setUserName(userDto.getUserName());
        if (userDto.getRole() != null)  user.setRole(userDto.getRole());
        if (userDto.getEmail() != null) user.setEmail(userDto.getEmail());
        if (userDto.getRegion() != null) user.setRegion(userDto.getRegion());
        if (userDto.getGender() != null) user.setGender(userDto.getGender());
        if (userDto.getPhoneNumber() != null) user.setPhoneNumber(userDto.getPhoneNumber());
        if (userDto.getAddress() != null) user.setAddress(userDto.getAddress());
        if (userDto.getIsAccountBlocked() != null) user.setIsAccountBlocked(userDto.getIsAccountBlocked());
        userRepository.save(user);
    }
    public class UpdateException extends Exception {
        public UpdateException(String message) {
            super(message);
        }
    }



    private Paginationdto convertIntoPagination(Customers customer){
        Paginationdto paginationdto=new Paginationdto();
        paginationdto.setCustomerId(customer.getCustomerId());
        paginationdto.setCustomerName(customer.getCustomerName());
        paginationdto.setRegion(customer.getRegion());
        paginationdto.setGender(customer.getGender());
        return paginationdto;
    }




    private Customers convertIntoCusotmerFromCustormerdto(Customerdto customer){
        Customers customers = new Customers();
        customers.setCustomerName(customer.getCustomerName());
        customers.setPhoneNumber(customer.getPhoneNumber());
        customers.setRegion(customer.getRegion());
        customers.setGender(customer.getGender());
        customers.setAddress(customer.getAddress());
        customers.setCustomerId(customers.getCustomerId());
        return customers;
    }


    private Customers convertIntoCustomersFromPagination(Paginationdto customer){
        Customers customers = new Customers();
        customers.setCustomerId(customer.getCustomerId());
        customers.setCustomerName(customer.getCustomerName());
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
        customerdto.setCustomerId(customer.getCustomerId());
        customerdto.setPhoneNumber(customer.getPhoneNumber());
        customerdto.setCustomerName(customer.getCustomerName());
        customerdto.setRegion(customer.getRegion());
        customerdto.setGender(customer.getGender());
        customerdto.setAddress(customer.getAddress());
        return customerdto;
    }




    
    public Page<Customers> SearchedCustomer(Integer offset,Integer pageSize,String text) throws NoCustomerExistException {
        String[] searchWords = text.split(" ");
        String queryPattern = "%";
        for (String word : searchWords) {
            queryPattern += word;
            queryPattern += '%';
        }
        Page<Object[]> searchResults = userRepository.searchCustomer(queryPattern, PageRequest.of(offset, pageSize));

        if (searchResults.getTotalElements() == 0) {
            throw new NoCustomerExistException("Customer Not Found");
        }

        Page<Customers> customersPage = searchResults.map(result -> {
            Customers customer = new Customers();
            Object customerIdObject = result[0];
            Long customerId = null;

            if (customerIdObject instanceof BigInteger) {
                customerId = ((BigInteger) customerIdObject).longValue();
            } else if (customerIdObject instanceof Long) {
                customerId = (Long) customerIdObject;
            } else {
                throw new IllegalArgumentException("Unexpected type for customer ID: " + customerIdObject.getClass().getName());
            }
            customer.setCustomerId(customerId);
            customer.setCustomerName((String) result[1]);
            customer.setGender((String) result[2]);
            customer.setRegion((String) result[3]);
            System.out.println(customer);
            return customer;
        });

        return customersPage;
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




