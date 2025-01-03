package com.artisa.artisa.dto;

import java.time.LocalDateTime;

public record CompletionConfirmationDto(
        Integer id,
        String clientName,
        String artisanName,
        String serviceName,
        Double servicePrice,
        String status,
        LocalDateTime dateCreation,
        LocalDateTime dateModification,
        LocalDateTime proposedCompletionDate,
        String notes,
        Boolean artisanConfirmed,
        Boolean clientConfirmed,
        Boolean bothConfirmed
) {}
