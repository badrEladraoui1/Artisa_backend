package com.artisa.artisa.service.impl;

import com.artisa.artisa.dto.PaymentRequestDto;
import com.artisa.artisa.dto.PaymentResponseDto;
import com.artisa.artisa.entity.Reservation;
import com.artisa.artisa.entity.Transaction;
import com.artisa.artisa.exception.ArtisaException;
import com.artisa.artisa.repository.ReservationRepo;
import com.artisa.artisa.repository.TransactionRepo;
import com.artisa.artisa.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// TransactionService.java
@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepo transactionRepository;
    private final ReservationRepo reservationRepository;

    @Override
    public PaymentResponseDto processPayment(PaymentRequestDto paymentRequest) {
        // Validate reservation
        Reservation reservation = reservationRepository.findById(paymentRequest.reservationId())
                .orElseThrow(() -> new ArtisaException(HttpStatus.NOT_FOUND, "Reservation not found"));

        // Validate amount (20% of service price)
        double expectedAmount = reservation.getService().getTarif() * 0.2;
        if (Math.abs(paymentRequest.amount() - expectedAmount) > 0.01) {
            throw new ArtisaException(HttpStatus.BAD_REQUEST, "Invalid payment amount");
        }

        // Create transaction
        Transaction transaction = new Transaction();
        transaction.setReservation(reservation);
        transaction.setDate(new Date());
        transaction.setMontant(paymentRequest.amount());
        transaction.setStatus("PENDING");
        transaction.setPaymentMethod(paymentRequest.paymentMethod());

        // Handle payment method specific details
        if ("PAYPAL".equals(paymentRequest.paymentMethod())) {
            transaction.setPaypalEmail(paymentRequest.paymentDetails().paypalEmail());
        } else if ("CARD".equals(paymentRequest.paymentMethod())) {
            String cardNumber = paymentRequest.paymentDetails().cardNumber();
            transaction.setCardLastFour(cardNumber.substring(cardNumber.length() - 4));
            transaction.setCardExpiryDate(paymentRequest.paymentDetails().expiryDate());
        }

        // Save initial transaction
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Process payment (mock successful payment)
        savedTransaction.setStatus("COMPLETED");
        savedTransaction = transactionRepository.save(savedTransaction);

        // Update reservation status
        reservation.setStatus("IN_PROGRESS");
        reservationRepository.save(reservation);

        return mapToResponseDto(savedTransaction);
    }

    private PaymentResponseDto mapToResponseDto(Transaction transaction) {
        return new PaymentResponseDto(
                transaction.getId(),
                transaction.getReservation().getId(),
                transaction.getStatus().toString(),
                transaction.getMontant(),
                transaction.getDate(),
                transaction.getPaymentMethod()
        );
    }

    @Override
    public List<PaymentResponseDto> getTransactionsByReservation(Integer reservationId) {
        return transactionRepository.findByReservationId(reservationId).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }
}