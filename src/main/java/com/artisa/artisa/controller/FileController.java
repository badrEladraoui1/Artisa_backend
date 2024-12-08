// FileController.java
package com.artisa.artisa.controller;

import com.artisa.artisa.service.impl.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/images")
@CrossOrigin(origins = "*")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;


    @GetMapping("/profile-pictures/{fileName:.+}")
    public ResponseEntity<Resource> getProfilePicture(@PathVariable String fileName) {
        Resource resource = fileStorageService.loadFileAsResource(fileName, "profile-pictures");

        return ResponseEntity.ok()
                .contentType(getMediaTypeForFileName(fileName))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/service-pictures/{fileName:.+}")
    public ResponseEntity<Resource> getServicePicture(@PathVariable String fileName) {
        Resource resource = fileStorageService.loadFileAsResource(fileName, "service-pictures");

        return ResponseEntity.ok()
                .contentType(getMediaTypeForFileName(fileName))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // Helper method to determine MediaType based on file extension
    private MediaType getMediaTypeForFileName(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        switch (extension) {
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            case "gif":
                return MediaType.IMAGE_GIF;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

//    @GetMapping("/{fileName:.+}")
//    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
//        Resource resource = fileStorageService.loadFileAsResource(fileName);
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.IMAGE_JPEG)
//                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
//    }
}