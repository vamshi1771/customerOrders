package com.example.Spring_boot_2.services.impl;
import com.example.Spring_boot_2.dto.*;
import com.example.Spring_boot_2.entity.OrderDetails;
import com.example.Spring_boot_2.entity.Orders;
import com.example.Spring_boot_2.entity.Products;
import com.example.Spring_boot_2.exceptions.NoOrderExistsException;
import com.example.Spring_boot_2.exceptions.NoOrdersForThisCustomer;
import com.example.Spring_boot_2.exceptions.OrderAlreadyExitsException;
import com.example.Spring_boot_2.exceptions.updateException;
import com.example.Spring_boot_2.repository.*;
import com.example.Spring_boot_2.services.OrderService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServicesImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;


    @Autowired
    private CustomerRepository customerRepository;


    @Transactional
    public ResponseEntity<String> registerOrder(OrdersDto ordersDto) {
        Orders ord = convertOrdersDtoIntoOrders(ordersDto);
        List<Products> products = productsRepository.findAllByIdWithLock(ordersDto.getProductQuantityMap().keySet());

        for (Products product : products) {
            Long productId = product.getProductId();
            Long quantityToDeduct = ordersDto.getProductQuantityMap().get(productId);

            if (product.getQuantity() < quantityToDeduct) {
                throw new updateException(product.getProductName() + " is out of stock");
            }
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrderId(ordersDto.getUser_id());
            orderDetails.setProductId(productId);
            orderDetails.setPrice(product.getPrice());
            orderDetails.setProductQuantity(quantityToDeduct);
            orderDetailsRepository.save(orderDetails);

            product.setQuantity(product.getQuantity() - quantityToDeduct);
        }
        productsRepository.saveAll(products);
        orderRepo.save(ord);
        return new ResponseEntity<>(
                "all is well",
                HttpStatus.OK);
    }

    public Double findOrderPrice(Map<Long,Long> productQuantity){
        Double totalPrice = Double.valueOf(0);
            List<Products> productsList = productsRepository.findAll();
        Map<Long, Double> productPriceMap = productsList.stream()
                .collect(Collectors.toMap(Products::getProductId, Products::getPrice));

        for (Map.Entry<Long, Long> entry : productQuantity.entrySet()) {
            Long productId = entry.getKey();
            Long quantity = entry.getValue();
            Double productPrice = productPriceMap.get(productId);
            if (productPrice != null) {
                totalPrice += productPrice * quantity;
            } else {
                throw new updateException("Product ID " + productId + " not found.");
            }
        }
        return totalPrice;
    }

    public Orders convertOrdersDtoIntoOrders(OrdersDto ordersDto){
        Orders order = new Orders();
        Date currentDate = new Date();
        order.setOrderDate(currentDate);
        Double totalPrice = findOrderPrice(ordersDto.getProductQuantityMap());
        order.setOrderPrice(totalPrice);
        order.setProductCount((long) ordersDto.getProductQuantityMap().size());
        order.setUserId(ordersDto.getUser_id());
        return order;
    }

    public List<Orderdto> getOrder(Long id)throws NoOrderExistsException {
        List<Orderdto> order= orderRepo.findById(id)
                .stream()
                .map(this::convertIntoDto).collect(Collectors.toList());

        if(order.size()==0){
            throw new OrderAlreadyExitsException("NO Order Exists");
        }
        return order;
    }

    public List<Orders> getAllOrders() {
        return orderRepo.findAll();

    }
   public List<Orderdto> getByRegion(String Name){
        return orderRepo.getByRegion(Name)
                .stream()
                .map(this::convertIntoDto).collect(Collectors.toList());
    }
//    public void UpdateOrder(Orders order,Integer order_id) {
//        String o_name = order.getOrdername();
//        orderRepo.Updateorder(o_name,order_id);
//    }
   public List<Orderdto> getOrdersByCustomer_Id(Long id)throws NoOrdersForThisCustomer {
       List<Orderdto> orderDto= orderRepo.getByCustomer_Id(id)
                .stream()
                .map(this::convertIntoDto)
                .collect(Collectors.toList());
       if(orderDto.size()==0){
           throw new NoOrdersForThisCustomer("No Orders For This Customer");
       }
       return orderDto;
    }
    private Orderdto convertIntoDto(Orders order){
        Orderdto orders = new Orderdto();
        orders.setOrderedDate(order.getOrderDate());
        orders.setOrderId(order.getOrderId());
        orders.setNumberOfProducts(order.getProductCount());
        orders.setUserId(order.getOrderId());
        orders.setPrice(order.getOrderPrice());
        return orders;
    }


    private pageableOrders convertIntoPageableOrders(Object[] object){
        pageableOrders pageableOrders = new pageableOrders();
        BigInteger orderId = (BigInteger) object[0];
        pageableOrders.setOrderId(orderId.longValue());
        pageableOrders.setOrderedDate((String) object[1]);
        Double price = (Double) object[2];
        pageableOrders.setPrice(price);
        pageableOrders.setCustomerName((String) object[3]);
        BigInteger sum = (BigInteger) object[4];
        pageableOrders.setProductCount(sum.longValue());
    return pageableOrders;
    }

    public orderList OrdersInPages(Integer offset, Integer pageSize) {
        int startIndex = (offset == 0) ? 0 : offset*pageSize;
        List<Object[]>orderList =  orderRepo.findOrdersInPages(startIndex,pageSize);
       List<Orders> orders = orderRepo.findAll();
       int ordersCount = orders.size();
       List<pageableOrders> pageableOrdersList = orderList.stream()
                   .map((item) -> convertIntoPageableOrders(item))
                   .collect(Collectors.toList());

       orderList orderList1 = new orderList();
       orderList1.setPageableOrdersList(pageableOrdersList);
       int pageCount = (int) Math.ceil((double) ordersCount/pageSize);
       orderList1.setPageCount((long) pageCount);
       orderList1.setPageIndex((long) offset+1);;
     return orderList1;
    }

    public dashboardDto getAllProducts(){
        dashboardDto regionsDto = new dashboardDto();
        List<String> products = orderRepo.getAllProducts();
        regionsDto.setRegions(products);
        Long customerCount = Long.valueOf(userRepository.findAll().size());
        regionsDto.setCustomersCount(customerCount);
        Integer orderCount = orderRepo.findAll().size();
        regionsDto.setOrdersCount(orderCount);
        Integer ProductCount = productsRepository.findAll().size();
        regionsDto.setProductsCount(ProductCount);
        Integer outOfStock = productsRepository.findOutOfStock();
        regionsDto.setOutOfStock(outOfStock);
        return regionsDto;
    }
}
