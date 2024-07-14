package com.example.Spring_boot_2.dto;

import com.example.Spring_boot_2.entity.Products;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class CustomersAndProductsDto {
    private List<Customerdto> customersList;
    private List<Products> productLists;
}
