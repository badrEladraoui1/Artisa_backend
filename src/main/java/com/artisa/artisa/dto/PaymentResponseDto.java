package com.artisa.artisa.dto;

import java.util.Date;

public record PaymentResponseDto(
        Integer id,
        Integer reservationId,
        String status,
        Double amount,
        Date date,
        String paymentMethod
) {}
