package com.example.Spring_boot_2.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "customers")
public class Customers {
    @Id

    @Column(name = "customer_id")
    private Integer customerid;
    @Column(name = "customer_name")
    private String customername;
    @Column(name = "region")
    private String region;
    @Column(name = "gender")
    private String gender;


}
