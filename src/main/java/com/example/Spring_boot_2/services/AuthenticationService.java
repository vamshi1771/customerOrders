package com.example.Spring_boot_2.services;

import com.example.Spring_boot_2.entity.User;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    public User register(User user);
    public ResponseEntity<?> login(User user);
}
