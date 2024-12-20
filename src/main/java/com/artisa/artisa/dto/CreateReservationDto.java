package com.artisa.artisa.dto;

import java.time.LocalDateTime;

public record CreateReservationDto(
        Integer clientId,
        Integer artisanId,
        Integer serviceId,
        String notes,
        LocalDateTime proposedCompletionDate
) {}