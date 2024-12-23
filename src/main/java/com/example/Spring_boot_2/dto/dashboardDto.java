package com.example.Spring_boot_2.dto;

import lombok.Data;

import java.util.List;

@Data
public class dashboardDto {
    private List<String> regions;
    private Long customersCount;
    private Integer ordersCount;
    private Integer productsCount;
    private Integer outOfStock;
}
