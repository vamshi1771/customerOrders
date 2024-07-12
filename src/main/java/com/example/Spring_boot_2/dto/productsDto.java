package com.example.Spring_boot_2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class productsDto {
    private String productName;
    private  Long price;
    private  Long quantity;
    private  Long productId;
}
