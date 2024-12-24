package com.example.Spring_boot_2.dto;

import lombok.Data;

import java.util.Date;

@Data
public class pageableOrders {
private Long orderId;
private String orderedDate;
private String customerName;
private Double price;
private Long productCount;
}
