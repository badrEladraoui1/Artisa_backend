package com.artisa.artisa.dto;

import java.util.List;

public record ArtisanDisplayDTO(
        Integer id,
        String nomComplet,
        String profilePictureFileName,
        String rating,  // this is static for now
        String adresse,
        String phone,
        String description,
        String metier,
        List<ServicePreviewDTO> recentServices
) {}
