package com.example.Spring_boot_2.services;


import com.example.Spring_boot_2.entity.FileUploader;
import com.example.Spring_boot_2.repository.FileUploaderRepository;
import com.example.Spring_boot_2.fileUploaderUtills.FileUploaderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class FileUploaderService {
    @Autowired
    FileUploaderRepository FileUploaderRepo;

    public String UploadFile(MultipartFile file) throws IOException {
    FileUploader FILE =    FileUploaderRepo.save(FileUploader.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .File(FileUploaderUtils.compressFile(file.getBytes())).build());
    if(FILE!=null) return file.getOriginalFilename()+ "  Uploaded Successfully";
    return null;
    }
    public byte[] downloadFile(String filename){
        Optional<FileUploader> File= FileUploaderRepo.findByName(filename);
        byte[] files=FileUploaderUtils.decompressFile(File.get().getFile());
        return files;
    }

}
