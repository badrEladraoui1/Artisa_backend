package com.artisa.artisa.dto;

public record PaymentRequestDto(
        Integer reservationId,
        String paymentMethod,
        Double amount,
        PaymentDetailsDto paymentDetails
) {}