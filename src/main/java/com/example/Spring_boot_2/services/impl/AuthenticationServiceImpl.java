package com.example.Spring_boot_2.services.impl;

import com.example.Spring_boot_2.entity.User;
import com.example.Spring_boot_2.repository.UserRepository;
import com.example.Spring_boot_2.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public ResponseEntity<?> login(User user)throws IllegalArgumentException {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            if (authentication.isAuthenticated()) {
                User loggedUser = userRepository.findByUserName(user.getUserName());
                return ResponseEntity.ok(loggedUser);
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Authentication failed: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Authentication failed: Invalid username or password.");
    }
}
