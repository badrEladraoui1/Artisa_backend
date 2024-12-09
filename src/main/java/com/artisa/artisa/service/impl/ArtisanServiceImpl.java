package com.artisa.artisa.service.impl;

import com.artisa.artisa.dto.*;
import com.artisa.artisa.entity.Artisan;
import com.artisa.artisa.exception.ArtisaException;
import com.artisa.artisa.repository.ArtisanRepo;
import com.artisa.artisa.repository.ServiceRepo;
import com.artisa.artisa.service.ArtisanService;
import com.artisa.artisa.service.JwtService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArtisanServiceImpl implements ArtisanService {

    ArtisanRepo artisanRepo;
    FileStorageService fileStorageService;
    ServiceRepo serviceRepo;
    JwtService jwtService;


    public ArtisanServiceImpl(ArtisanRepo artisanRepo , FileStorageService fileStorageService , ServiceRepo serviceRepo , JwtService jwtService) {
        this.artisanRepo = artisanRepo;
        this.fileStorageService = fileStorageService;
        this.serviceRepo = serviceRepo;
        this.jwtService = jwtService;

    }

    private Artisan getAuthenticatedArtisan(String token) {
        Integer artisanId = jwtService.getUserIdFromToken(token);
        return artisanRepo.findById(artisanId)
                .orElseThrow(() -> new ArtisaException(HttpStatus.NOT_FOUND, "Artisan not found"));
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

    @Override
    public List<ArtisanDisplayDTO> getAllArtisans() {
        return artisanRepo.findAllByOrderByDateInscriptionDesc().stream()
                .map(artisan -> {
                    // Get last 2 services for this artisan
                    List<ServicePreviewDTO> recentServices = serviceRepo.findTop2ByArtisanOrderByDateCreationDesc(artisan)
                            .stream()
                            .map(service -> new ServicePreviewDTO(
                                    service.getTitre(),
                                    service.getTarif()
                            ))
                            .collect(Collectors.toList());

                    return new ArtisanDisplayDTO(
                            artisan.getId(),
                            artisan.getNomComplet(),
                            artisan.getProfilePictureFileName(),
                            "⭐⭐⭐⭐⭐ (48 reviews)",  // Static for now
                            artisan.getAdresse(),
                            artisan.getPhone(),
                            artisan.getDescription(),
                            artisan.getMetier(),
                            recentServices
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public ArtisanDetailDTO getArtisanDetails(Integer artisanId) {

        Artisan artisan = artisanRepo.findById(artisanId)
                .orElseThrow(() -> new ArtisaException(HttpStatus.NOT_FOUND, "Artisan not found"));

        List<ServiceDetailDTO> services = serviceRepo.findAllByArtisan_Id(artisanId)
                .stream()
                .map(service -> new ServiceDetailDTO(
                        service.getId(),
                        service.getTitre(),
                        service.getDescription(),
                        service.getTarif(),
                        service.getServicePictureFileName(),
                        service.getDateCreation()
                ))
                .collect(Collectors.toList());

        return new ArtisanDetailDTO(
                artisan.getId(),
                artisan.getNomComplet(),
                artisan.getProfilePictureFileName(),
                artisan.getEmail(),
                artisan.getAdresse(),
                artisan.getPhone(),
                artisan.getDescription(),
                artisan.getMetier(),
                services
        );
    }

}
