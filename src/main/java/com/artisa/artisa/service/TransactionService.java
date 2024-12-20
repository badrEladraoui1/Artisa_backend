package com.artisa.artisa.service;

import com.artisa.artisa.dto.PaymentRequestDto;
import com.artisa.artisa.dto.PaymentResponseDto;

import java.util.List;

public interface TransactionService {
    PaymentResponseDto processPayment(PaymentRequestDto paymentRequest);
    List<PaymentResponseDto> getTransactionsByReservation(Integer reservationId);
}
