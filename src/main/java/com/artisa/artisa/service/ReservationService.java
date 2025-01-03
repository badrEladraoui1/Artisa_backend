package com.artisa.artisa.service;

import com.artisa.artisa.dto.*;
import com.artisa.artisa.enums.ReservationStatus;
import java.util.List;

public interface ReservationService {
    ReservationResponseDto createReservation(String token, CreateReservationDto dto);
    ReservationResponseDto updateReservationStatus(String token, Integer reservationId, UpdateReservationStatusDto dto);
    List<ReservationResponseDto> getClientReservations(String token, Integer clientId);
    List<ReservationResponseDto> getArtisanReservations(String token, Integer artisanId);
    List<ReservationResponseDto> getReservationsByStatus(String token, Integer userId, String status);
    ReservationResponseDto getReservationById(String token, Integer reservationId);
    CompletionConfirmationDto confirmCompletion(String token, Integer reservationId, String userType);
    ReservationResponseDto submitReview(String token, Integer reservationId, ReviewDto review);
    List<ReviewDto> getArtisanReviews(String token, Integer artisanId);
    double getArtisanAverageRating(String token, Integer artisanId);
    List<ReviewDto> getArtisanCompletedReviewedReservations(String token, Integer artisanId);
}