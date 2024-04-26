package com.example.Spring_boot_2.controllers;

import com.example.Spring_boot_2.entity.FileUploader;
import com.example.Spring_boot_2.services.FileUploaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileUploaderController {
    @Autowired
    FileUploaderService fileUploaderService;

    @PostMapping("/FileUploader")
    public ResponseEntity<?> UploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileUpload = fileUploaderService.UploadFile(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(fileUpload);
    }

    @GetMapping("/{fieName}")
    public ResponseEntity<?> downloadFile(@PathVariable String fieName) {
        byte[] file = fileUploaderService.downloadFile(fieName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/pdf"))
                .body(file);
    }
}
