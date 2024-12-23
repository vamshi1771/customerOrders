package com.example.Spring_boot_2.dto;

import lombok.*;

import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OrdersDto {

    private Map<Long,Long> productQuantityMap;
    private Long user_id;
    private Long orderPrice;
}
