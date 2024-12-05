package com.artisa.artisa.service.impl;

import com.artisa.artisa.entity.Artisan;
import com.artisa.artisa.repository.ArtisanRepo;
import com.artisa.artisa.service.ArtisanService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArtisanServiceImpl implements ArtisanService {

    ArtisanRepo artisanRepo;
    FileStorageService fileStorageService;


    public ArtisanServiceImpl(ArtisanRepo artisanRepo , FileStorageService fileStorageService) {
        this.artisanRepo = artisanRepo;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public ResponseEntity<Resource> getProfilePicture(Integer id) {
        try {
            Optional<Artisan> artisan = artisanRepo.findById(id);
            String fileName = "";
            if(artisan.isPresent()){
                 fileName = artisan.get().getProfilePictureFileName();
                if (fileName == null || fileName.isEmpty()) {
                    return ResponseEntity.notFound().build();
                }
            }

            Resource resource = fileStorageService.loadFileAsResource(fileName);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("image/jpeg"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
