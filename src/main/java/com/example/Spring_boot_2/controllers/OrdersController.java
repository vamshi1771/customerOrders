package com.example.Spring_boot_2.controllers;
import com.example.Spring_boot_2.dto.Orderdto;
import com.example.Spring_boot_2.entity.Orders;
import com.example.Spring_boot_2.services.impl.OrderServicesImpl;
import com.example.Spring_boot_2.exceptions.NoOrderExistsException;
import com.example.Spring_boot_2.exceptions.NoOrdersForThisCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin("*")
public class OrdersController {

    @Autowired
    OrderServicesImpl obj1;

    @PostMapping("/saveOrder")
    public void SaveOrder(@RequestBody Orderdto order) {
        System.out.println(order.toString());
        obj1.SaveOrder(order);
    }

    @GetMapping("/getAllOrders")
    public List<Orders> getOrders() {
        return obj1.getAllOrders();
    }

    @GetMapping("/getOrder/{id}")
    public List<Orderdto> getOrder(@PathVariable Integer id) throws NoOrderExistsException {
        return obj1.getOrder(id);
    }


    @PutMapping("/UpdateOrder/{order_id}")
    public void UpdateOrder(@RequestBody Orders order, @PathVariable Integer order_id) {
        obj1.UpdateOrder(order, order_id);
    }

    @GetMapping("/getByRegion/{Name}")
    public List<Orderdto> getByRegion(@PathVariable String Name) {
        return obj1.getByRegion(Name);
    }

    @GetMapping("/getOrdersByCustomerId/{id}")
    public List<Orderdto> getOrdersByCustomerId(@PathVariable Integer id) throws NoOrdersForThisCustomer {
        return obj1.getOrdersByCustomer_Id(id);
    }


    @GetMapping("/getAllOrdersInPages/{offset}/{pageSize}")
    public Page<Orders> getAllOrdersInPages(@PathVariable Integer offset, @PathVariable Integer pageSize) {
        return obj1.OrdersInPages(offset, pageSize);
    }

    @GetMapping("/getSearchOrders/{offset}/{pageSize}/{text}")
    public Page<Orderdto> SearchedOrders(@PathVariable Integer offset, @PathVariable Integer pageSize, @PathVariable String text) throws NoOrderExistsException {
        return obj1.SearchedOrders(offset, pageSize, text);
    }
}