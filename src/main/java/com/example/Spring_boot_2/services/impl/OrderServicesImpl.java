package com.example.Spring_boot_2.services.impl;
import com.example.Spring_boot_2.dto.Orderdto;
import com.example.Spring_boot_2.dto.orderList;
import com.example.Spring_boot_2.dto.pageableOrders;
import com.example.Spring_boot_2.dto.regionsDto;
import com.example.Spring_boot_2.entity.Orders;
import com.example.Spring_boot_2.entity.Products;
import com.example.Spring_boot_2.exceptions.NoOrderExistsException;
import com.example.Spring_boot_2.exceptions.NoOrdersForThisCustomer;
import com.example.Spring_boot_2.exceptions.OrderAlreadyExitsException;
import com.example.Spring_boot_2.repository.CustomerRepository;
import com.example.Spring_boot_2.repository.OrderRepository;
import com.example.Spring_boot_2.repository.ProductsRepository;
import com.example.Spring_boot_2.services.OrderService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServicesImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private ProductsRepository productsRepository;


    @Autowired
    private CustomerRepository customerRepository;
    public ResponseEntity<String>  SaveOrder(Orderdto order) {
        Orders ord= convertInto(order);
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
//        orders.setProductId(order.getProductId());
        orders.setCustomerId(order.getCustomerId());
        orders.setPrice(order.getPrice());
//        orders.setNumberOfProducts(order.getNoOfProducts());
        return orders;
    }
    private Orders convertInto(Orderdto order){
        Orders orders = new Orders();
//        orders.setProductId(order.getProductId());
        orders.setCustomerId(order.getCustomerId());
        orders.setPrice(order.getPrice());
//        orders.setNoOfProducts(order.getNumberOfProducts());
        return orders;
    }

    private pageableOrders convertIntoPageableOrders(Object[] object){
        pageableOrders pageableOrders = new pageableOrders();
        BigInteger orderId = (BigInteger) object[0];
        pageableOrders.setOrderId(orderId.longValue());
        pageableOrders.setPrice((String) object[1]);
        pageableOrders.setProductName((String) object[2]);
        pageableOrders.setCustomerName((String) object[3]);
        BigInteger bigInteger = (BigInteger) object[4];
        pageableOrders.setProductCount(bigInteger.longValue());
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

    public regionsDto getAllProducts(){
        regionsDto regionsDto = new regionsDto();
        List<String> products = orderRepo.getAllProducts();
        regionsDto.setRegions(products);
        Integer customerCount =  customerRepository.findAll().size();
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
