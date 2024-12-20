package com.artisa.artisa.controller;

import com.artisa.artisa.dto.PaymentRequestDto;
import com.artisa.artisa.dto.PaymentResponseDto;
import com.artisa.artisa.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/process")
    public ResponseEntity<PaymentResponseDto> processPayment(
            @RequestHeader("Authorization") String token,
            @RequestBody PaymentRequestDto paymentRequest
    ) {
        return ResponseEntity.ok(transactionService.processPayment(paymentRequest));
    }

    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<List<PaymentResponseDto>> getTransactionsByReservation(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer reservationId
    ) {
        return ResponseEntity.ok(transactionService.getTransactionsByReservation(reservationId));
    }
}
