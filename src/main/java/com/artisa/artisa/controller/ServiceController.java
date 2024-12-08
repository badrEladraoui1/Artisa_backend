package com.artisa.artisa.controller;

import com.artisa.artisa.dto.ServiceDTO;
import com.artisa.artisa.dto.ServiceResponseDTO;
import com.artisa.artisa.entity.Service;
import com.artisa.artisa.service.ServiceService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
@CrossOrigin(origins = "http://localhost:4200")
public class ServiceController {
    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }


    @PreAuthorize("hasRole('ARTISAN')")
    @PostMapping(value = "/create"
//            ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> createService(@RequestHeader("Authorization") String token, @ModelAttribute ServiceDTO serviceDTO) {
        return ResponseEntity.ok(serviceService.createService(token,serviceDTO));
    }

    @PutMapping("/{serviceId}/update")
    @PreAuthorize("hasRole('ARTISAN')")
    public ResponseEntity<String> updateService(
            @PathVariable Integer serviceId,
            @RequestHeader("Authorization") String token,
            @ModelAttribute ServiceDTO serviceDTO
    ) {
        return ResponseEntity.ok(serviceService.updateService(serviceId, token, serviceDTO));
    }

    @DeleteMapping("/{serviceId}/delete")
    @PreAuthorize("hasRole('ARTISAN')")
    public ResponseEntity<String> deleteService(
            @PathVariable Integer serviceId,
            @RequestHeader("Authorization") String token
    ) {
        return ResponseEntity.ok(serviceService.deleteService(serviceId, token));
    }

    @GetMapping("/artisan/{artisanId}")
    public ResponseEntity<List<ServiceResponseDTO>> getArtisanServices(
            @PathVariable Integer artisanId,
            @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(serviceService.getArtisanServices(artisanId, token));
    }
}
