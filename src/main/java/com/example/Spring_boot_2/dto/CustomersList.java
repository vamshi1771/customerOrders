package com.example.Spring_boot_2.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomersList {
    List<customersPageableDto> customersPageable ;
    Long PageCount ;
    Long pageIndex;

}
