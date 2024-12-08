package com.artisa.artisa.service.impl;

import com.artisa.artisa.dto.ServiceDTO;
import com.artisa.artisa.dto.ServiceResponseDTO;
import com.artisa.artisa.entity.Artisan;
import com.artisa.artisa.exception.ArtisaException;
import com.artisa.artisa.mappers.ServiceMapper;
import com.artisa.artisa.repository.ArtisanRepo;
import com.artisa.artisa.repository.ServiceRepo;
import com.artisa.artisa.service.JwtService;
import com.artisa.artisa.service.ServiceService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepo serviceRepo;
    private final ArtisanRepo artisanRepo;
    private final FileStorageService fileStorageService;
    private final JwtService jwtService;
    private final ServiceMapper serviceMapper;

    public ServiceServiceImpl(ServiceRepo serviceRepo, ArtisanRepo artisanRepo, ServiceMapper serviceMapper,
                          FileStorageService fileStorageService , JwtService jwtService) {
        this.serviceRepo = serviceRepo;
        this.artisanRepo = artisanRepo;
        this.fileStorageService = fileStorageService;
        this.jwtService = jwtService;
        this.serviceMapper = serviceMapper;
    }

    private Artisan getAuthenticatedArtisan(String token) {
        Integer artisanId = jwtService.getUserIdFromToken(token);
        return artisanRepo.findById(artisanId)
                .orElseThrow(() -> new ArtisaException(HttpStatus.NOT_FOUND, "Artisan not found"));
    }


    @Transactional
    @Override
    public String createService(String token, ServiceDTO serviceDTO) {
        if (!jwtService.isTokenValid(token)) {
            throw new ArtisaException(HttpStatus.UNAUTHORIZED, "Invalid or missing token");
        }

        Artisan artisan = getAuthenticatedArtisan(token);

        com.artisa.artisa.entity.Service service = new com.artisa.artisa.entity.Service();
        service.setTitre(serviceDTO.titre());
        service.setDescription(serviceDTO.description());
        service.setTarif(serviceDTO.tarif());
        service.setCategorie(artisan.getMetier());
        service.setArtisan(artisan);

        if (serviceDTO.servicePicture() != null && !serviceDTO.servicePicture().isEmpty()) {
            String fileName = fileStorageService.storeFile(
                    serviceDTO.servicePicture(),
                    "service_" + artisan.getId(),
                    "service-pictures"
            );
            service.setServicePictureFileName(fileName);
        }
         serviceRepo.save(service);
        return "Service created successfully";
    }

    @Transactional
    @Override
    public String updateService(Integer serviceId, String token, ServiceDTO serviceDTO) {
        if (!jwtService.isTokenValid(token)) {
            throw new ArtisaException(HttpStatus.UNAUTHORIZED, "Invalid or missing token");
        }

        // Get authenticated artisan
        Artisan artisan = getAuthenticatedArtisan(token);

        // Find the service and verify ownership
        com.artisa.artisa.entity.Service service = serviceRepo.findById(serviceId)
                .orElseThrow(() -> new ArtisaException(HttpStatus.NOT_FOUND, "Service not found"));

        if (!service.getArtisan().getId().equals(artisan.getId())) {
            throw new ArtisaException(HttpStatus.FORBIDDEN, "You can only update your own services");
        }

        // Update basic fields
        service.setTitre(serviceDTO.titre());
        service.setDescription(serviceDTO.description());
        service.setTarif(serviceDTO.tarif());

        // Handle image update if new image is provided
        if (serviceDTO.servicePicture() != null && !serviceDTO.servicePicture().isEmpty()) {
            // Delete old image if it exists
            if (service.getServicePictureFileName() != null) {
                try {
                    Path oldImagePath = Paths.get(fileStorageService.getFileStorageLocation().toString(),
                            "service-pictures", service.getServicePictureFileName());
                    Files.deleteIfExists(oldImagePath);
                } catch (IOException e) {
                    // Log error but continue with update
                    System.err.println("Could not delete old image: " + e.getMessage());
                }
            }

            // Store new image
            String fileName = fileStorageService.storeFile(
                    serviceDTO.servicePicture(),
                    "service_" + artisan.getId(),
                    "service-pictures"
            );
            service.setServicePictureFileName(fileName);
        }

        serviceRepo.save(service);
        return "Service updated successfully";
    }

    @Transactional
    @Override
    public String deleteService(Integer serviceId, String token) {
        if (!jwtService.isTokenValid(token)) {
            throw new ArtisaException(HttpStatus.UNAUTHORIZED, "Invalid or missing token");
        }

        // Get authenticated artisan
        Artisan artisan = getAuthenticatedArtisan(token);

        // Find the service and verify ownership
        com.artisa.artisa.entity.Service service = serviceRepo.findById(serviceId)
                .orElseThrow(() -> new ArtisaException(HttpStatus.NOT_FOUND, "Service not found"));

        if (!service.getArtisan().getId().equals(artisan.getId())) {
            throw new ArtisaException(HttpStatus.FORBIDDEN, "You can only delete your own services");
        }

        // Delete the image file if it exists
        if (service.getServicePictureFileName() != null) {
            try {
                Path imagePath = Paths.get(fileStorageService.getFileStorageLocation().toString(),
                        "service-pictures", service.getServicePictureFileName());
                Files.deleteIfExists(imagePath);
            } catch (IOException e) {
                // Log error but continue with deletion
                System.err.println("Could not delete service image: " + e.getMessage());
            }
        }

        // Delete the service from database
        serviceRepo.delete(service);
        return "Service deleted successfully";
    }

    @Override
    public List<ServiceResponseDTO> getArtisanServices(Integer artisanId, String token) {
        if (!jwtService.isTokenValid(token)) {
            throw new ArtisaException(HttpStatus.UNAUTHORIZED, "Invalid token");
        }

        List<com.artisa.artisa.entity.Service> services = serviceRepo.findAllByArtisan_Id(artisanId);
        if (services == null) {
            return new ArrayList<>();
        }

        return services.stream()
                .map(serviceMapper::toDTO)
                .collect(Collectors.toList());
    }
}