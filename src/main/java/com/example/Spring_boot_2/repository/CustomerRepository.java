package com.example.Spring_boot_2.repository;

import com.example.Spring_boot_2.entity.Customers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;


public interface CustomerRepository extends JpaRepository<Customers, Long> {

    @Query(value = "SELECT * FROM customers WHERE lower(customer_name) like lower(:text)", nativeQuery = true)
    Page<Object[]> searchcustomer(String text, Pageable pr);

    @Query(value = "SELECT * FROM customers  OFFSET :offset LIMIT :pagesize", nativeQuery = true)
    List<Customers> getCustomerInPages(Integer offset, Integer pagesize);

    @Query(value = "select u.user_id as customerId,u.user_name as customerName," +
            " u.region as region, u.gender as gender, COUNT(o.user_id) as orderCount" +
            " from users as u LEFT JOIN orders as o" +
            " ON u.user_id = o.user_id" +
            " group by u.user_id" +
            " order by u.user_id ASC" +
            " Limit ?2 offset ?1", nativeQuery = true)
    List<Object[]> getAllCustomerInPages(Integer offset, Integer pageSize);

    @Query(value = "SELECT region FROM customers WHERE role = 'CUSTOMER'", nativeQuery = true)
    List<String> getCustomerRegions();

    @Query(value = "SELECT customer_name FROM customers where lower(customer_name) like lower(?1)", nativeQuery = true)
    List<String> findByCustomerName(String name);

    List<Customers> findBycustomerIdIn(Collection<Long> customer_id);

}
