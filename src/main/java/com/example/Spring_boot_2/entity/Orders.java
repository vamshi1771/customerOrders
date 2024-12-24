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
    @SequenceGenerator(name = "order_id_gen", sequenceName = "order_id_seq", initialValue = 1,allocationSize = 1)
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "order_price")
    private Double orderPrice;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "order_date")
    private Date orderDate;
    @Column(name = "product_count")
    private Long productCount;
}
