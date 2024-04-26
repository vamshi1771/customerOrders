package com.example.Spring_boot_2.services.impl;
import com.example.Spring_boot_2.dto.Orderdto;
import com.example.Spring_boot_2.entity.Orders;
import com.example.Spring_boot_2.exceptions.NoOrderExistsException;
import com.example.Spring_boot_2.exceptions.NoOrdersForThisCustomer;
import com.example.Spring_boot_2.exceptions.OrderAlreadyExitsException;
import com.example.Spring_boot_2.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServicesImpl {

    @Autowired
    private OrderRepository orderRepo;
    public void SaveOrder(Orderdto order) throws OrderAlreadyExitsException {
        Orders ord= convertInto(order);
        System.out.println(ord);
        Integer num =order.getOrderId();
       if(orderRepo.findById(num).isPresent()){
           throw new OrderAlreadyExitsException("Order Already Exists");
       }
        orderRepo.save(ord);
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
    public void UpdateOrder(Orders order,Integer order_id) {
        String o_name = order.getOrdername();
        orderRepo.Updateorder(o_name,order_id);
    }
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
        Orderdto orderdto = new Orderdto();
        orderdto.setPrice(order.getPrice());
        orderdto.setOrderName(order.getOrdername());
        orderdto.setOrderId(order.getOrderid());
        orderdto.setCustId(order.getCust_id());
        return orderdto;
    }
    private Orders convertInto(Orderdto order){
        Orders orders = new Orders();
        orders.setOrderid(order.getOrderId());
        orders.setOrdername(order.getOrderName());
        orders.setCust_id(order.getCustId());
        orders.setPrice(order.getPrice());
        return orders;
    }




    public Page<Orderdto> SearchedOrders(Integer offset, Integer pageSize, String text) throws NoOrderExistsException {

        Page<Object[]> obj =  orderRepo.searchOrder(text,PageRequest.of(offset,pageSize));
        if(obj.getTotalPages()==0) {
            throw new NoOrderExistsException("Order Not Present");
        }
        System.out.println(obj.get());
        Page<Orderdto> pg = obj.map(item -> {
            Orderdto cst = new Orderdto();
            cst.setOrderId((Integer) item[0]);
            cst.setPrice((Integer) item[1]);
            cst.setOrderName((String) item[2]);
            cst.setCustId((Integer) item[3]);

            return cst;
        });
        return pg;
    }

    public Page<Orders> OrdersInPages(Integer offset, Integer pageSize) {
        return orderRepo.findAll(PageRequest.of(offset,pageSize));
    }
}
