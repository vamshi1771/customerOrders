package com.example.Spring_boot_2.repository;

import com.example.Spring_boot_2.entity.Customers;
import com.example.Spring_boot_2.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;


public interface OrderRepository extends JpaRepository<Orders, Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE orders SET order_name =:o_name WHERE id=:id", nativeQuery = true)
    void Updateorder(String o_name, Integer id);


    @Query(value = "select o1.order_id as orderId, o1.price as price, p1.product_name as productName, c1.customer_name as customerName, o1.no_of_products as noOfProducts" +
            " from ((orders as o1" +
            " LEFT JOIN products as p1 ON o1.product_id = p1.product_id)" +
            " inner join customers as c1 ON o1.customer_id = c1.customer_id)" +
            " order by o1.order_id" +
            " Limit ?2 offset ?1", nativeQuery = true)
    List<Object[]> findOrdersInPages(Integer offset, Integer pageSize);

    @Query(value = "SELECT * FROM orders  JOIN customers ON orders.cust_id=customers.customer_id WHERE customers.region=:Name", nativeQuery = true)
    List<Orders> getByregion(String Name);

    @Query(value = "SELECT * FROM orders JOIN customers on orders.cust_id= customers.customer_id WHERE cust_id=:id", nativeQuery = true)
    List<Orders> getByCustomer_Id(Integer id);


    @Query(value = "SELECT * FROM orders WHERE lower(order_name) like concat ('%', lower(:text), '%')", nativeQuery = true)
    Page<Object[]> searchOrder(String text, Pageable pr);

    @Query(value = "SELECT * FROM orders  OFFSET :offset LIMIT :pageSize", nativeQuery = true)
    List<Customers> getOrdersInPages(Integer offset, Integer pageSize);


    @Query(value = "SELECT Order_name FROM orders", nativeQuery = true)
    List<String> getBysameOrders();

    @Query(value = "SELECT p1.product_name FROM products as p1 join orders o1 on o1.product_id = p1.product_id ", nativeQuery = true)
    List<String> getAllProducts();
}
