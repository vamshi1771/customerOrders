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
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "order_price")
    private Double orderPrice;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "order_date")
    private Date orderDate;
}
