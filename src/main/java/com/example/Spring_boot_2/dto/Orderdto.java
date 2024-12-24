package com.example.Spring_boot_2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Orderdto {
    private Date orderedDate;
    private Double price;
    private Long userId;
    private Long numberOfProducts;
    private Long orderId;
}

