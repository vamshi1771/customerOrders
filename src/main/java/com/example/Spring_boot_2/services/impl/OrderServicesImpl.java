package com.example.Spring_boot_2.services.impl;
import com.example.Spring_boot_2.dto.Orderdto;
import com.example.Spring_boot_2.entity.Orders;
import com.example.Spring_boot_2.entity.Products;
import com.example.Spring_boot_2.exceptions.NoOrderExistsException;
import com.example.Spring_boot_2.exceptions.NoOrdersForThisCustomer;
import com.example.Spring_boot_2.exceptions.OrderAlreadyExitsException;
import com.example.Spring_boot_2.repository.CustomerRepository;
import com.example.Spring_boot_2.repository.OrderRepository;
import com.example.Spring_boot_2.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServicesImpl {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CustomerRepository customerRepository;
    public ResponseEntity<String>  SaveOrder(Orderdto order) {
        Orders ord= convertInto(order);
        System.out.println(ord);
        Long productId =order.getProductId();
        Integer customerId =  order.getCustomerId();
       if(!customerRepository.findById(customerId).isPresent()){
           throw  new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"No Customer exits with this CustomerID");
//           throw new OrderAlreadyExitsException("No Customer exits with this CustomerID");
       }
        if(!productsRepository.findById(productId).isPresent()){
            throw  new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"No product exits with this getProductId");

//            throw new OrderAlreadyExitsException("No product exits with this getProductId");
        }
        Optional<Products> products =productsRepository.findById(productId);
        if(productsRepository.findById(productId).isPresent()){

            if(products.get().getQuantity() < order.getNumberOfProducts()){
                throw  new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,"Required Stock not available for the product");
            }

        }
         Long number =  products.get().getQuantity() - order.getNumberOfProducts();
        productsRepository.updateProductQuanatity(productId,number);
        orderRepo.save(ord);
        return new ResponseEntity<>(
                "all is well",
                HttpStatus.OK);

    }
    public List<Orderdto> getOrder(Integer id)throws NoOrderExistsException {
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
        return orderRepo.getByregion(Name)
                .stream()
                .map(this::convertIntoDto).collect(Collectors.toList());
    }
//    public void UpdateOrder(Orders order,Integer order_id) {
//        String o_name = order.getOrdername();
//        orderRepo.Updateorder(o_name,order_id);
//    }
   public List<Orderdto> getOrdersByCustomer_Id(Integer id)throws NoOrdersForThisCustomer {
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
        orders.setProductId(order.getProductId());
        orders.setCustomerId(order.getCustomerId());
        orders.setPrice(order.getPrice());
        orders.setCustomerName(order.getCustomerName());
        orders.setNumberOfProducts(order.getNoOfProducts());
        return orders;
    }
    private Orders convertInto(Orderdto order){
        Orders orders = new Orders();
        orders.setProductId(order.getProductId());
        orders.setCustomerId(order.getCustomerId());
        orders.setPrice(order.getPrice());
        orders.setCustomerName(order.getCustomerName());
        orders.setNoOfProducts(order.getNumberOfProducts());
        return orders;
    }




//    public Page<Orderdto> SearchedOrders(Integer offset, Integer pageSize, String text) throws NoOrderExistsException {
//
//        Page<Object[]> obj =  orderRepo.searchOrder(text,PageRequest.of(offset,pageSize));
//        if(obj.getTotalPages()==0) {
//            throw new NoOrderExistsException("Order Not Present");
//        }
//        System.out.println(obj.get());
//        Page<Orderdto> pg = obj.map(item -> {
//            Orderdto cst = new Orderdto();
//            cst.setOrderId((Integer) item[0]);
//            cst.setPrice((Integer) item[1]);
//            cst.setOrderName((String) item[2]);
//            cst.setCustId((Integer) item[3]);
//
//            return cst;
//        });
//        return pg;
//    }

    public Page<Orders> OrdersInPages(Integer offset, Integer pageSize) {
        return orderRepo.findAll(PageRequest.of(offset,pageSize));
    }
}
