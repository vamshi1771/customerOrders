package com.example.Spring_boot_2.services;

import com.example.Spring_boot_2.dto.FormWrapper;
import com.example.Spring_boot_2.dto.productsDto;
import com.example.Spring_boot_2.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {

    void saveProduct(String productName,Long price,Long quantity,MultipartFile file) throws IOException;

    void updateProduct(String productName,Long price,Long quantity,MultipartFile file,Long productId) throws IOException;
    Page<Products> productsInPages(Integer offset, Integer pageSize);
}
