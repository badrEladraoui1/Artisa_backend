package com.artisa.artisa.service;

import com.artisa.artisa.dto.CreateReservationDto;
import com.artisa.artisa.dto.ReservationResponseDto;
import com.artisa.artisa.dto.UpdateReservationStatusDto;
import com.artisa.artisa.enums.ReservationStatus;
import java.util.List;

public interface ReservationService {
    ReservationResponseDto createReservation(String token, CreateReservationDto dto);
    ReservationResponseDto updateReservationStatus(String token, Integer reservationId, UpdateReservationStatusDto dto);
    List<ReservationResponseDto> getClientReservations(String token, Integer clientId);
    List<ReservationResponseDto> getArtisanReservations(String token, Integer artisanId);
    List<ReservationResponseDto> getReservationsByStatus(String token, Integer userId, String status);
    ReservationResponseDto getReservationById(String token, Integer reservationId);
}