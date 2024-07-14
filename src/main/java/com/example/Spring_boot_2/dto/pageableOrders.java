package com.example.Spring_boot_2.dto;

import lombok.Data;

@Data
public class pageableOrders {
private Long orderId;
private String ProductName;
private String customerName;
private String price;
private Long productCount;
}
