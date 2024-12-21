package com.example.Spring_boot_2.services;

import com.example.Spring_boot_2.entity.User;
import com.example.Spring_boot_2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public interface AuthenticationService {

    public User register(User user);
    public User login(User user);
}
