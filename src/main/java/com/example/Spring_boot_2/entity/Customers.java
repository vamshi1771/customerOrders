package com.example.Spring_boot_2.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "customers")
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "customer_id")
    private Integer customerid;
    @Column(name = "customer_name")
    private String customername;
    @Column(name = "region")
    private String region;
    @Column(name = "gender")
    private String gender;
    @Column (name = "phone_number")
    private String phoneNumber;
    @Column (name = "address")
    private String address;
}
