package com.example.Spring_boot_2.services.impl;

import com.cloudinary.Cloudinary;
import com.example.Spring_boot_2.services.CloudinaryImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryImageServiceImp implements CloudinaryImageService {

    @Autowired
    private  Cloudinary cloudinary;
    @Override
    public String upload(MultipartFile file) {
        try {
         Map data =   this.cloudinary.uploader().upload(file.getBytes(), Map.of());
            Map<String, Object> imageInfo = data;
            String secureUrl = (String) imageInfo.get("secure_url");
           return  secureUrl;
        }
        catch (IOException error){
            throw new RuntimeException("Image uploaded failed");
        }
    }
}
