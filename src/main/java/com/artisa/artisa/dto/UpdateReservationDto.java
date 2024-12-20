// UpdateReservationDto.java
package com.artisa.artisa.dto;

import java.time.LocalDateTime;

public record UpdateReservationDto(
        Integer clientId,
        Integer artisanId,
        Integer serviceId,
        String notes,
        LocalDateTime proposedCompletionDate,
        String status
) {}