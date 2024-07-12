package com.example.Spring_boot_2.controllers;

import com.example.Spring_boot_2.dto.FormWrapper;
import com.example.Spring_boot_2.dto.productsDto;
import com.example.Spring_boot_2.entity.Products;
import com.example.Spring_boot_2.services.impl.ProductServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class ProductsController {

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("/saveProduct/{productName}/{price}/{quantity}")
    void saveProducts(@RequestParam("image") MultipartFile file,
                      @PathVariable String productName,
                      @PathVariable Long price,
                      @PathVariable Long quantity) throws IOException {
        productService.saveProduct(productName,price,quantity,file);
    }
    @GetMapping("/getAllProducts/{offset}/{pageSize}")
   public Page<Products> getAllProducts(@PathVariable Integer offset, @PathVariable Integer pageSize){
        if(pageSize == null) pageSize = 20;
      return productService.productsInPages(offset,pageSize);
    }
    @PostMapping("/updateProduct/{productName}/{price}/{quantity}/{productId}")
    void updateProducts(@RequestParam("image") MultipartFile file,
                      @PathVariable String productName,
                      @PathVariable Long price,
                      @PathVariable Long quantity,@PathVariable Long productId) throws IOException {
        productService.updateProduct(productName,price,quantity,file,productId);
    }


}
