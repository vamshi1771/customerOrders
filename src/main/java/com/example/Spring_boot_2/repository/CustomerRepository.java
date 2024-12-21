package com.example.Spring_boot_2.repository;

import com.example.Spring_boot_2.entity.Customers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;


public interface CustomerRepository extends JpaRepository<Customers, Integer> {

    @Query(value = "SELECT * FROM customers WHERE lower(customer_name) like lower(:text)", nativeQuery = true)
    Page<Object[]> searchcustomer(String text, Pageable pr);

    @Query(value = "SELECT * FROM customers  OFFSET :offset LIMIT :pagesize", nativeQuery = true)
    List<Customers> getCustomerInPages(Integer offset, Integer pagesize);

    @Query(value = "select  c1.customer_id as customerId,c1.customer_name as customerName," +
            " c1.region as region, c1.gender as gender, COUNT(p1.customer_id) as orderCount" +
            " from customers as c1 LEFT JOIN orders as p1" +
            " ON c1.customer_id = p1.customer_id" +
            " group by c1.customer_id" +
            " order by c1.customer_id ASC" +
            " Limit ?2 offset ?1", nativeQuery = true)
    List<Object[]> getAllCustomerInPages(Integer offset, Integer pagesize);

    @Query(value = "SELECT region FROM customers", nativeQuery = true)
    List<String> getCustomerregions();

    @Query(value = "SELECT customer_name FROM customers where lower(customer_name) like lower(?1)", nativeQuery = true)
    List<String> findByCustomerName(String name);

    List<Customers> findBycustomerIdIn(Collection<Long> customer_id);

}
