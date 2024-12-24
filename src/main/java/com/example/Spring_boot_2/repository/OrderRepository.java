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


public interface OrderRepository extends JpaRepository<Orders, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE orders SET order_name =:o_name WHERE id=:id", nativeQuery = true)
    void Updateorder(String o_name, Integer id);


    @Query(value = "select o1.order_id as orderId, TO_CHAR(o1.order_date, 'YYYY-MM-DD') as orderedDate, o1.order_price as price,  c1.user_name as customerName, o1.product_count as noOfProducts" +
            " from (orders o1" +
            " inner join users c1 ON o1.user_id = c1.user_id)" +
            " order by o1.order_id" +
            " Limit ?2 offset ?1", nativeQuery = true)
    List<Object[]> findOrdersInPages(Integer offset, Integer pageSize);

    @Query(value = "SELECT * FROM orders  JOIN users ON orders.user_id = users.user_id WHERE user.region = :Name", nativeQuery = true)
    List<Orders> getByRegion(String Name);

    @Query(value = "SELECT * FROM orders JOIN users on orders.user_id = users.user_id WHERE users.user_id = :id", nativeQuery = true)
    List<Orders> getByCustomer_Id(Long id);


    @Query(value = "SELECT * FROM orders WHERE lower(order_name) like concat ('%', lower(:text), '%')", nativeQuery = true)
    Page<Object[]> searchOrder(String text, Pageable pr);

    @Query(value = "SELECT * FROM orders  OFFSET :offset LIMIT :pageSize", nativeQuery = true)
    List<Customers> getOrdersInPages(Integer offset, Integer pageSize);


    @Query(value = "SELECT Order_name FROM orders", nativeQuery = true)
    List<String> getBysameOrders();

    @Query(value = "SELECT details.product_name as productName from order_details as details", nativeQuery = true)
    List<String> getAllProducts();
}
