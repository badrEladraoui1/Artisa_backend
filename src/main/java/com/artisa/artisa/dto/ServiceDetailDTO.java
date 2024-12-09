package com.artisa.artisa.dto;

import java.time.LocalDateTime;

public record ServiceDetailDTO(
        Integer id,
        String titre,
        String description,
        Double tarif,
        String servicePictureFileName,
        LocalDateTime dateCreation
) {}
