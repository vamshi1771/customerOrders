package com.example.Spring_boot_2.dto;

import lombok.Data;

import java.util.List;

@Data
public class orderList {
    private List<pageableOrders> pageableOrdersList;
    private  Long pageCount;
    private  Long pageIndex;
}
