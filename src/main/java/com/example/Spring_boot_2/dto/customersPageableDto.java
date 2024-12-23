package com.example.Spring_boot_2.dto;

import lombok.Data;

@Data
public class customersPageableDto {
    private  Long customerId;
    private  String customerName;
    private  String region;
    private String gender;
    private  Long orderCount;
}
