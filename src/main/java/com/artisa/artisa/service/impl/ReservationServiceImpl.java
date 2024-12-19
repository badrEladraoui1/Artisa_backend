package com.artisa.artisa.service.impl;

import com.artisa.artisa.dto.CreateReservationDto;
import com.artisa.artisa.dto.ReservationResponseDto;
import com.artisa.artisa.dto.UpdateReservationStatusDto;
import com.artisa.artisa.entity.*;
import com.artisa.artisa.enums.ReservationStatus;
import com.artisa.artisa.exception.ArtisaException;
import com.artisa.artisa.repository.*;
import com.artisa.artisa.service.ReservationService;
import com.artisa.artisa.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepo reservationRepository;
    private final ClientRepo clientRepository;
    private final ArtisanRepo artisanRepository;
    private final ServiceRepo serviceRepository;
    private final JwtService jwtService;

    private void validateToken(String token) {
        if (!token.startsWith("Bearer ")) {
            throw new ArtisaException(HttpStatus.UNAUTHORIZED, "Invalid token format");
        }
        String jwt = token.substring(7);
        try {
            jwtService.isTokenValid(jwt);
        } catch (Exception e) {
            throw new ArtisaException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        }
    }

    @Override
    @Transactional
    public ReservationResponseDto createReservation(String token, CreateReservationDto dto) {
        validateToken(token);

        Client client = clientRepository.findById(dto.clientId())
                .orElseThrow(() -> new ArtisaException(HttpStatus.NOT_FOUND, "Client not found"));

        Artisan artisan = artisanRepository.findById(dto.artisanId())
                .orElseThrow(() -> new ArtisaException(HttpStatus.NOT_FOUND, "Artisan not found"));

        com.artisa.artisa.entity.Service service = serviceRepository.findById(dto.serviceId())
                .orElseThrow(() -> new ArtisaException(HttpStatus.NOT_FOUND, "Service not found"));

        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setArtisan(artisan);
        reservation.setService(service);
        reservation.setNotes(dto.notes());
        reservation.setMontant(service.getTarif()); // Set amount from service price
        reservation.setStatus("PENDING"); // Set initial status

        Reservation savedReservation = reservationRepository.save(reservation);
        return mapToResponseDto(savedReservation);
    }

    @Override
    @Transactional
    public ReservationResponseDto updateReservationStatus(String token, Integer reservationId, UpdateReservationStatusDto dto) {
        validateToken(token);

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ArtisaException(HttpStatus.NOT_FOUND, "Reservation not found"));

        reservation.setStatus(dto.status());
        reservation.setNotes(dto.notes());

        return mapToResponseDto(reservationRepository.save(reservation));
    }

    @Override
    public List<ReservationResponseDto> getClientReservations(String token, Integer clientId) {
        validateToken(token);
        return reservationRepository.findByClientId(clientId).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationResponseDto> getArtisanReservations(String token, Integer artisanId) {
        validateToken(token);
        return reservationRepository.findByArtisanId(artisanId).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationResponseDto> getReservationsByStatus(String token, Integer userId, String status) {
        validateToken(token);
        return reservationRepository.findByClientIdAndStatus(userId, status).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationResponseDto getReservationById(String token, Integer reservationId) {
        validateToken(token);
        return reservationRepository.findById(reservationId)
                .map(this::mapToResponseDto)
                .orElseThrow(() -> new ArtisaException(HttpStatus.NOT_FOUND, "Reservation not found"));
    }

    private ReservationResponseDto mapToResponseDto(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getClient().getNomComplet(),
                reservation.getArtisan().getNomComplet(),
                reservation.getService().getTitre(),
                reservation.getService().getTarif(),
                reservation.getStatus(),
                reservation.getDateCreation(),
                reservation.getDateModification(),
                reservation.getNotes()
        );
    }
}