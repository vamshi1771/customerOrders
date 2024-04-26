package com.example.Spring_boot_2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Paginationdto {

    private Integer customerId;
    private String customerName;
    private String region;
    private String gender;
    private Integer count;
}
