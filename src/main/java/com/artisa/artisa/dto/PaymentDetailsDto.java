package com.artisa.artisa.dto;

public record PaymentDetailsDto(
        String cardNumber,
        String expiryDate,
        String cvv,
        String paypalEmail
) {}