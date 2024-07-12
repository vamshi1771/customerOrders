package com.example.Spring_boot_2.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryImageService {
    public String upload(MultipartFile file) throws IOException;
}

