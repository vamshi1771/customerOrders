package com.example.Spring_boot_2.configuration;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Coudinaryconfig {
    @Bean
    public Cloudinary getCloudinary(){
        Map config = new HashMap<>();
        config.put("cloud_name","dt2ddtaqc");
        config.put("api_key","362842357828512");
        config.put("api_secret","yItzy7Jd6VzdbvfO8mV7IHUN6kE");
        config.put("secure",true);
        return new Cloudinary(config);
    }
}
