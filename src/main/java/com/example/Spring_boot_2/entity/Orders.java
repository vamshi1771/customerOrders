package com.example.Spring_boot_2.entity;

import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

@Entity
@Table(name = "orders")
public class    Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "price")
    private String price;
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "order_date")
    private Date orderDate;

}
