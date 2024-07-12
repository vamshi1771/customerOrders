package com.example.Spring_boot_2.services.impl;

import com.example.Spring_boot_2.dto.FormWrapper;
import com.example.Spring_boot_2.dto.productsDto;
import com.example.Spring_boot_2.entity.Products;
import com.example.Spring_boot_2.repository.ProductsRepository;
import com.example.Spring_boot_2.services.CloudinaryImageService;
import com.example.Spring_boot_2.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductsRepository productsRepository;

    @Autowired
    private CloudinaryImageService cloudinaryImageService;

    private static final Logger logger = LoggerFactory.getLogger(DownloadCustomerDataImp.class);

    @Override
    public void saveProduct(String productName,Long price,Long quantity,MultipartFile file) throws IOException {
        logger.info("Saving new Product");
        String data  = this.cloudinaryImageService.upload(file);
        System.out.println("data"+data);
        Products product = new Products();
        product.setProductName(productName);
        product.setQuantity(quantity);
        product.setPrice(price);
        product.setPhotoUrl(data);
        productsRepository.save(product);
    }

    @Override
    public void updateProduct(String productName,Long price,Long quantity,MultipartFile file,Long productId) throws IOException {
        logger.info("Saving new Product");

        String data  = this.cloudinaryImageService.upload(file);
        System.out.println("data"+data);
        Products product = new Products();
        product.setProductName(productName);
        product.setQuantity(quantity);
        product.setPrice(price);
        product.setPhotoUrl(data);
        productsRepository.updateProduct(productName,price,quantity,data,productId);
    }

    @Override
   public Page<Products> productsInPages(Integer offset, Integer pageSize){
        return productsRepository.findAll(PageRequest.of(offset,pageSize));
    }




   public Products convertProductsDtoToProducts(productsDto productDto){
       Products product = new Products();
       product.setProductName(productDto.getProductName());
       product.setProductId(productDto.getProductId());
       product.setPrice(productDto.getPrice());
       product.setQuantity(productDto.getQuantity());
       product.setDelete(false);

       return product;
    }

    public productsDto convertProductsToProductsDto(Products products){
        productsDto productsDto =new productsDto();
        productsDto.setProductName(products.getProductName());
        productsDto.setPrice(products.getPrice());
        productsDto.setQuantity(products.getQuantity());
        return  productsDto;
    }



}
