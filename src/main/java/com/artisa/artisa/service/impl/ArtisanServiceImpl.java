package com.artisa.artisa.service.impl;

import com.artisa.artisa.dto.ArtisanProfileDTO;
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

            Resource resource = fileStorageService.loadFileAsResource(fileName , "profile-pictures");

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("image/jpeg"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> updateProfile(Integer id, ArtisanProfileDTO profileDTO) {
        try {
            Optional<Artisan> artisanOpt = artisanRepo.findById(id);
            if (artisanOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Artisan artisan = artisanOpt.get();
            artisan.setNomComplet(profileDTO.nomComplet());
            artisan.setEmail(profileDTO.email());
            artisan.setPhone(profileDTO.phone());
            artisan.setAdresse(profileDTO.address());

            artisanRepo.save(artisan);

            return ResponseEntity.ok().body("Profile updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update profile: " + e.getMessage());
        }
    }

}
