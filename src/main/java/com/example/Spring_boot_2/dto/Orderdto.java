package com.example.Spring_boot_2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Orderdto {
    private Integer orderId;
    private String orderName;
    private Integer price;
    private Integer custId;
}

