package com.example.Spring_boot_2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FormWrapper
{
    private MultipartFile image;
    private  String productName;
    private  Long price;
    private  Long quantity;
}
