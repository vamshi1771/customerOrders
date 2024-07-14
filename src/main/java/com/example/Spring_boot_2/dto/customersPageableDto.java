package com.example.Spring_boot_2.dto;

import lombok.Data;

@Data
public class customersPageableDto {
    private  Integer customerId;
    private  String customerName;
    private  String region;
    private String gender;
    private  Long orderCount;

}
