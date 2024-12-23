package com.example.Spring_boot_2.repository;

import com.example.Spring_boot_2.dto.UserDto;
import com.example.Spring_boot_2.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {


    User findByUserName(String username);
    List<User> findByRole(String role);

    @Query(value = "SELECT COUNT(*) FROM users WHERE role = 'CUSTOMER'", nativeQuery = true)
    Long getTotalCustomers();

    @Query(value = "SELECT region FROM users WHERE role = 'CUSTOMER'", nativeQuery = true)
    List<String> getCustomerRegions();

    @Query(value = "select u.user_id as customerId,u.user_name as customerName," +
            " u.region as region, u.gender as gender, COUNT(o.user_id) as orderCount" +
            " from users as u LEFT JOIN orders as o" +
            " ON u.user_id = o.user_id" +
            " group by u.user_id" +
            " order by u.user_id ASC" +
            " Limit ?2 offset ?1", nativeQuery = true)
    List<Object[]> getAllCustomerInPages(Integer offset, Integer pageSize);

    @Query(value = "SELECT user_id,user_name,gender,region FROM users where role = 'CUSTOMER' AND lower(user_name) like lower(:text)",
            countQuery = "SELECT COUNT(*) FROM users WHERE role = 'CUSTOMER'",
            nativeQuery = true)
    Page<Object[]> searchCustomer(String text, Pageable pr);

}
