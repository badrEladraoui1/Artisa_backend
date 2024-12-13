package com.artisa.artisa.controller;

import com.artisa.artisa.dto.ArtisanDetailDTO;
import com.artisa.artisa.dto.ArtisanProfileDTO;
import com.artisa.artisa.service.ArtisanService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.artisa.artisa.dto.ArtisanDisplayDTO;

import java.util.List;

@RestController
@RequestMapping("/artisans")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ArtisanController {

    private final ArtisanService artisanService;

    public ArtisanController(ArtisanService artisanService) {
        this.artisanService = artisanService;
    }

    @PreAuthorize("hasRole('ARTISAN')")
    @GetMapping("/{id}/profile-picture")
    public ResponseEntity<Resource> getProfilePicture(@Valid @PathVariable Integer id) {
        return artisanService.getProfilePicture(id);
    }

    @PreAuthorize("hasRole('ARTISAN')")
    @PutMapping("/{id}/profile")
    public ResponseEntity<?> updateProfile(@Valid @PathVariable Integer id,@Valid @RequestBody ArtisanProfileDTO profileDTO
    ) {
        return artisanService.updateProfile(id, profileDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ArtisanDisplayDTO>> getAllArtisans() {
        return ResponseEntity.ok(artisanService.getAllArtisans());
    }

    @GetMapping("/{artisanId}/details")
    public ResponseEntity<ArtisanDetailDTO> getArtisanDetails(@PathVariable Integer artisanId) {
        return ResponseEntity.ok(artisanService.getArtisanDetails(artisanId ));
    }

}
