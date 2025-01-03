package com.artisa.artisa.controller;

import com.artisa.artisa.dto.*;
import com.artisa.artisa.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponseDto> createReservation(
            @RequestHeader("Authorization") String token,
            @RequestBody CreateReservationDto dto) {
        return ResponseEntity.ok(reservationService.createReservation(token, dto));
    }

    @PutMapping("/{reservationId}/status")
    public ResponseEntity<ReservationResponseDto> updateStatus(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer reservationId,
            @RequestBody UpdateReservationStatusDto dto) {
        return ResponseEntity.ok(reservationService.updateReservationStatus(token, reservationId, dto));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ReservationResponseDto>> getClientReservations(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer clientId) {
        return ResponseEntity.ok(reservationService.getClientReservations(token, clientId));
    }

    @GetMapping("/artisan/{artisanId}")
    public ResponseEntity<List<ReservationResponseDto>> getArtisanReservations(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer artisanId) {
        return ResponseEntity.ok(reservationService.getArtisanReservations(token, artisanId));
    }

//    @GetMapping("/status")
//    public ResponseEntity<List<ReservationResponseDto>> getReservationsByStatus(
//            @RequestHeader("Authorization") String token,
//            @RequestParam Integer userId,
//            @RequestParam String status) {
//        return ResponseEntity.ok(reservationService.getReservationsByStatus(token, userId, status));
//    }

    @GetMapping("/client/{clientId}/completed")
    public ResponseEntity<List<ReservationResponseDto>> getClientCompletedReservations(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer clientId) {
        return ResponseEntity.ok(reservationService.getReservationsByStatus(token, clientId, "COMPLETED"));
    }

    @GetMapping("/client/{clientId}/accepted")
    public ResponseEntity<List<ReservationResponseDto>> getClientAcceptedReservations(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer clientId) {
        return ResponseEntity.ok(reservationService.getReservationsByStatus(token, clientId, "ACCEPTED"));
    }

    @GetMapping("/client/{clientId}/canceled")
    public ResponseEntity<List<ReservationResponseDto>> getClientCanceledReservations(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer clientId) {
        return ResponseEntity.ok(reservationService.getReservationsByStatus(token, clientId, "CANCELED"));
    }

    @GetMapping("/client/{clientId}/inprogress")
    public ResponseEntity<List<ReservationResponseDto>> getClientInProgressReservations(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer clientId) {
        return ResponseEntity.ok(reservationService.getReservationsByStatus(token, clientId, "IN_PROGRESS"));
    }

    @GetMapping("/client/{clientId}/suggested_by_artisan")
    public ResponseEntity<List<ReservationResponseDto>> getClientSuggestedByArtisandReservations(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer clientId) {
        return ResponseEntity.ok(reservationService.getReservationsByStatus(token, clientId, "SUGGESTED_BY_ARTISAN"));
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationResponseDto> getReservationById(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer reservationId) {
        return ResponseEntity.ok(reservationService.getReservationById(token, reservationId));
    }

    @PostMapping("/{reservationId}/confirm-completion/{userType}")
    public ResponseEntity<CompletionConfirmationDto> confirmCompletion(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer reservationId,
            @PathVariable String userType
    ) {
        return ResponseEntity.ok(
                reservationService.confirmCompletion(token, reservationId, userType)
        );
    }

    @PostMapping("/{reservationId}/review")
    public ResponseEntity<ReservationResponseDto> submitReview(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer reservationId,
            @RequestBody ReviewDto review
    ) {
        return ResponseEntity.ok(
                reservationService.submitReview(token, reservationId, review)
        );
    }

    @GetMapping("/artisan/{artisanId}/reviews")
    public ResponseEntity<List<ReviewDto>> getArtisanReviews(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer artisanId) {
        return ResponseEntity.ok(reservationService.getArtisanReviews(token, artisanId));
    }

    @GetMapping("/artisan/{artisanId}/average-rating")
    public ResponseEntity<Double> getArtisanAverageRating(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer artisanId) {
        return ResponseEntity.ok(reservationService.getArtisanAverageRating(token, artisanId));
    }

    @GetMapping("/artisan/{artisanId}/completed-reviews")
    public ResponseEntity<List<ReviewDto>> getArtisanCompletedReviews(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer artisanId) {
        return ResponseEntity.ok(
                reservationService.getArtisanCompletedReviewedReservations(token, artisanId)
        );
    }
}