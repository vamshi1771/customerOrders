package com.example.Spring_boot_2.repository;

import com.example.Spring_boot_2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {


//    @Query(value = "SELECT * FROM users WHERE user_name = :1",nativeQuery = true)
//    User findByName(String userName);

    User findByUserName(String username);
}
