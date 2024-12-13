package com.artisa.artisa.dto;

import java.time.LocalDateTime;

public record ServiceResponseDTO(
        Integer id,
        String categorie,
        LocalDateTime dateCreation,
        String description,
        String servicePictureFileName,
        Double tarif,
        String titre,
        ArtisanDTO artisan
) {}
