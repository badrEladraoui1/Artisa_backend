package com.artisa.artisa.dto;

public record CreateReservationDto(
        Integer clientId,
        Integer artisanId,
        Integer serviceId,
        String notes
) {}