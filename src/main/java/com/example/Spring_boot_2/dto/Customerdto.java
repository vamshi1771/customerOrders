package com.example.Spring_boot_2.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Customerdto {
    private Long customerId;
    private String phoneNumber;
    private String customerName;
    private String region;
    private String gender;
    private String address;
}
