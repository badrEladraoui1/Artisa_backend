package com.artisa.artisa.controller;

import com.artisa.artisa.dto.CreateReservationDto;
import com.artisa.artisa.dto.ReservationResponseDto;
import com.artisa.artisa.dto.UpdateReservationStatusDto;
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
}