package com.example.Spring_boot_2.controllers;


import com.example.Spring_boot_2.entity.User;
import com.example.Spring_boot_2.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/register")
    public User register(@RequestBody User user){
    return authenticationService.register(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user){
        return authenticationService.login(user);
    }
}
