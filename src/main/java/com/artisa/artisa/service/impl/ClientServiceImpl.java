package com.artisa.artisa.service.impl;

import com.artisa.artisa.entity.Client;
import com.artisa.artisa.repository.ClientRepo;
import com.artisa.artisa.service.ClientService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    ClientRepo clientRepo;
    FileStorageService fileStorageService;

    public ClientServiceImpl(ClientRepo clientRepo , FileStorageService fileStorageService) {
        this.clientRepo = clientRepo;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public ResponseEntity<Resource> getProfilePicture(Integer id) {
        try {
            Optional<Client> client = clientRepo.findById(id);
            String fileName = "";
            if(client.isPresent()){
                fileName = client.get().getProfilePictureFileName();
                if (fileName == null || fileName.isEmpty()) {
                    return ResponseEntity.notFound().build();
                }
            }

            Resource resource = fileStorageService.loadFileAsResource(fileName , "profile-pictures");

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("image/jpeg"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
