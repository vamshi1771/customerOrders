package com.example.Spring_boot_2.entity;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "price")
    private Long price;
    @Column(name = "quantity")
    private Long quantity;
    @Column (name = "is_delete")
    private boolean isDelete;
    @Column(name ="photo_url")
    private String photoUrl;

}
