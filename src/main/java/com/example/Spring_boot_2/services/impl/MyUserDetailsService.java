package com.example.Spring_boot_2.services.impl;

import com.example.Spring_boot_2.entity.User;
import com.example.Spring_boot_2.entity.UserPrincipal;
import com.example.Spring_boot_2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user1 = userRepository.findByUserName(username);
        if(user1 == null){
            throw new UsernameNotFoundException("User Not Found");
        }

        return new UserPrincipal(user1);
    }
}
