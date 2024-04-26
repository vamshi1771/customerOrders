package com.example.Spring_boot_2.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer orderid;
    @Column(name = "order_name")
    private String ordername;
    @Column(name = "price")
    private Integer price;
    @Column(name = "cust_id")
    private Integer cust_id;

}
