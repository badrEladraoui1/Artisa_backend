package com.artisa.artisa.dto;

import com.artisa.artisa.enums.ReservationStatus;

import java.time.LocalDateTime;

public record ReservationResponseDto(
        Integer id,
        String clientName,
        String artisanName,
        String serviceName,
        Double servicePrice,
        String status,
        LocalDateTime dateCreation,
        LocalDateTime dateModification,
        String notes
) {}
