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
    @SequenceGenerator(name = "product_id_seq_gen", sequenceName = "product_id_seq", initialValue = 1,allocationSize = 1)
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "price")
    private Double price;
    @Column(name = "quantity")
    private Long quantity;
    @Column (name = "is_delete")
    private boolean isDelete;
    @Column(name ="photo_url")
    private String photoUrl;

}
