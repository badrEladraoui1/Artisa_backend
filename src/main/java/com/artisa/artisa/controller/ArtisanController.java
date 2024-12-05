package com.artisa.artisa.controller;

import com.artisa.artisa.service.ArtisanService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artisans")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ArtisanController {

    private ArtisanService artisanService;

    public ArtisanController(ArtisanService artisanService) {
        this.artisanService = artisanService;
    }

    @PreAuthorize("hasRole('ARTISAN')")
    @GetMapping("/{id}/profile-picture")
    public ResponseEntity<Resource> getProfilePicture(@PathVariable Integer id) {
        return artisanService.getProfilePicture(id);
    }

}
