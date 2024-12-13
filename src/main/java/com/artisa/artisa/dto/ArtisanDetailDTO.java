package com.artisa.artisa.dto;

import java.util.List;

public record ArtisanDetailDTO(
        Integer id,
        String nomComplet,
        String profilePictureFileName,
        String email,
        String adresse,
//        String rating,
        String phone,
        String description,
        String metier,
        List<ServiceDetailDTO> services
) {}
