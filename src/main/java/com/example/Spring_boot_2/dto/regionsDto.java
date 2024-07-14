package com.example.Spring_boot_2.dto;

import lombok.Data;

import java.util.List;

@Data
public class regionsDto {
    private List<String> regions;
    private Integer customersCount;
    private Integer ordersCount;
    private Integer productsCount;
    private Integer outOfStock;
}
