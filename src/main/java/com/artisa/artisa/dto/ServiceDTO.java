package com.artisa.artisa.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record ServiceDTO(
        @NotBlank(message = "Title is required")
        String titre,

        @NotBlank(message = "Description is required")
        String description,

        @NotNull(message = "Price is required")
        @Min(value = 0, message = "Price must be positive")
        Double tarif,

        MultipartFile servicePicture
) {}