package com.example.Spring_boot_2.repository;

import com.example.Spring_boot_2.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductsRepository extends JpaRepository<Products, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE products SET quantity = ?2 WHERE product_id = ?1", nativeQuery = true)
   void updateProductQuanatity(Long productId,Long noOfProducts);
    @Modifying
    @Transactional
    @Query(value = "UPDATE products SET product_name = ?1, price = ?2, quantity = ?3,photo_url = ?4  WHERE product_id = ?5", nativeQuery = true)
    void updateProduct(String productName,Long price,Long quantity,String photoUrl,Long ProductId);

    @Query(value = "select count(product_id) as count from products where quantity ='0'", nativeQuery = true)
    Integer findOutOfStock();

}
