package com.example.Spring_boot_2.controllers;
import com.example.Spring_boot_2.dto.Orderdto;
import com.example.Spring_boot_2.dto.OrdersDto;
import com.example.Spring_boot_2.dto.orderList;
import com.example.Spring_boot_2.dto.dashboardDto;
import com.example.Spring_boot_2.entity.Orders;
import com.example.Spring_boot_2.repository.OrderRepository;
import com.example.Spring_boot_2.services.impl.OrderServicesImpl;
import com.example.Spring_boot_2.exceptions.NoOrderExistsException;
import com.example.Spring_boot_2.exceptions.NoOrdersForThisCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin("*")
public class OrdersController {

    @Autowired
    OrderServicesImpl obj1;

    @Autowired
    OrderRepository orderRepository;


    @PostMapping("/registerOrder")
    public void registerOrder(@RequestBody OrdersDto ordersDto) {
        obj1.registerOrder(ordersDto);
    }

    @GetMapping("/getAllOrders")
    public List<Orders> getOrders() {
        return obj1.getAllOrders();
    }

    @GetMapping("/getOrder/{id}")
    public List<Orderdto> getOrder(@PathVariable Integer id) throws NoOrderExistsException {
        return obj1.getOrder(Long.valueOf((id)));
    }

    @GetMapping("/getByRegion/{Name}")
    public List<Orderdto> getByRegion(@PathVariable String Name) {
        return obj1.getByRegion(Name);
    }

    @GetMapping("/getOrdersByCustomerId/{id}")
    public List<Orderdto> getOrdersByCustomerId(@PathVariable Long id) throws NoOrdersForThisCustomer {
        return obj1.getOrdersByCustomer_Id(id);
    }

    @GetMapping("/GetAllOrderedProducts")
    public dashboardDto getAllOrdersProducts(){
       return  obj1.getAllProducts();
    }

    @GetMapping("/getAllOrdersInPages/{offset}/{pageSize}")
    public orderList getAllOrdersInPages(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        return obj1.OrdersInPages(offset, pageSize);
    }


}