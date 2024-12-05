// FileStorageService.java
package com.artisa.artisa.service.impl;

import com.artisa.artisa.exception.ArtisaException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir)
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new ArtisaException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not create the directory where the uploaded files will be stored.");
        }
    }

    public String storeFile(MultipartFile file, String userId) {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = userId + "_" + System.currentTimeMillis() + fileExtension;

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new ArtisaException(HttpStatus.BAD_REQUEST,
                        "Filename contains invalid path sequence");
            }

            // Copy file to the target location
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new ArtisaException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not store file " + fileName);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()) {
                return resource;
            } else {
                throw new ArtisaException(HttpStatus.NOT_FOUND, "File not found");
            }
        } catch (MalformedURLException ex) {
            throw new ArtisaException(HttpStatus.NOT_FOUND, "File not found");
        }
    }
}