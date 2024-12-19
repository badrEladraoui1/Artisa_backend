package com.artisa.artisa.dto;

import com.artisa.artisa.enums.ReservationStatus;

// For updating reservation status
public record UpdateReservationStatusDto(
        String status,
        String notes
) {}
