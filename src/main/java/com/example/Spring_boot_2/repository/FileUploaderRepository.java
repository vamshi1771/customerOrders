package com.example.Spring_boot_2.repository;

import com.example.Spring_boot_2.entity.FileUploader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface FileUploaderRepository extends JpaRepository<FileUploader, Long> {


    @Modifying
    @Transactional
    @Query(value = "SELECT * FROM file_uploader WHERE name=:fileName", nativeQuery = true)
    Optional<FileUploader> findByName(String fileName);
}
