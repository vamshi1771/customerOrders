package com.example.Spring_boot_2.controllers;


import com.example.Spring_boot_2.entity.Customers;
import com.example.Spring_boot_2.entity.User;
import com.example.Spring_boot_2.repository.CustomerRepository;
import com.example.Spring_boot_2.repository.UserRepository;
import com.example.Spring_boot_2.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;

@RestController
@CrossOrigin("*")
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/register")
    public User register(@RequestBody User user){
        Date currentDate = new Date();
        user.setRegisteredDate(currentDate);
        User user1 = authenticationService.register(user);
        return user1;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        return authenticationService.login(user);
    }
}
