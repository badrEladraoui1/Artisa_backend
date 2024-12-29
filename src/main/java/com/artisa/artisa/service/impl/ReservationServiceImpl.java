package com.artisa.artisa.service.impl;

import com.artisa.artisa.dto.*;
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

import java.time.LocalDateTime;
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
    private final ReviewRepo reviewRepository;
    private final UtilisateurRepo utilisateurRepo;


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
        reservation.setProposedCompletionDate(dto.proposedCompletionDate());
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

        // Check if a review exists for this reservation
        boolean reviewed = reviewRepository.existsByReservationId(reservation.getId());

        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getClient().getNomComplet(),
                reservation.getArtisan().getNomComplet(),
                reservation.getService().getTitre(),
                reservation.getService().getTarif(),
                reservation.getStatus(),
                reservation.getDateCreation(),
                reservation.getDateModification(),
                reservation.getNotes(),
                reservation.getProposedCompletionDate(),
                Boolean.TRUE.equals(reservation.getArtisanConfirmed()),
                Boolean.TRUE.equals(reservation.getClientConfirmed()),
                reviewed
        );
    }

    @Override
    public CompletionConfirmationDto confirmCompletion(String token, Integer reservationId, String userType) {
        validateToken(token);

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ArtisaException(HttpStatus.NOT_FOUND, "Reservation not found"));

        // Verify the status
        if (!"IN_PROGRESS".equals(reservation.getStatus())) {
            throw new ArtisaException(HttpStatus.BAD_REQUEST,
                    "Only in-progress reservations can be confirmed");
        }

        // Update confirmation based on user type
        if ("CLIENT".equalsIgnoreCase(userType)) {
            reservation.setClientConfirmed(true);
        } else if ("ARTISAN".equalsIgnoreCase(userType)) {
            reservation.setArtisanConfirmed(true);
        } else {
            throw new ArtisaException(HttpStatus.BAD_REQUEST, "Invalid user type");
        }

        // Check if both have confirmed
        boolean bothConfirmed = reservation.getClientConfirmed() &&
                reservation.getArtisanConfirmed();

        // If both confirmed, update status and set completion date
        if (bothConfirmed) {
            reservation.setStatus("COMPLETED");
            reservation.setCompletionDate(LocalDateTime.now());
        }

        Reservation savedReservation = reservationRepository.save(reservation);

        return mapToCompletionConfirmationDto(savedReservation, bothConfirmed);
    }

    @Override
    @Transactional
    public ReservationResponseDto submitReview(String token, Integer reservationId, ReviewDto reviewDto) {
        // Validate token
        validateToken(token);

        // Validate rating
        if (reviewDto.rating() < 1 || reviewDto.rating() > 5) {
            throw new ArtisaException(HttpStatus.BAD_REQUEST,
                    "Rating must be between 1 and 5");
        }

        // Find the reservation
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ArtisaException(HttpStatus.NOT_FOUND, "Reservation not found"));

        // Verify the status
        if (!"COMPLETED".equals(reservation.getStatus())) {
            throw new ArtisaException(HttpStatus.BAD_REQUEST,
                    "Only completed reservations can be reviewed");
        }

        // Get the current user (client) from the token
        String userEmail = jwtService.getUsernameFromToken(token.substring(7));
        Client reviewer = clientRepository.findByNomComplet(userEmail)
                .orElseThrow(() -> new ArtisaException(HttpStatus.NOT_FOUND, "Reviewer not found"));

        // Create new Review entity
        Review review = new Review();
        review.setReservation(reservation);
        review.setRating(reviewDto.rating());
        review.setComment(reviewDto.comment());
        review.setReviewDate(LocalDateTime.now());
        review.setReviewer(reviewer);
        review.setReviewedArtisan(reservation.getArtisan());

        // Save the review
        reviewRepository.save(review);

//        // Update reservation notes
//        reservation.setNotes("Rating: " + reviewDto.rating() + " stars. " +
//                (reviewDto.comment() != null ? reviewDto.comment() : "No additional comment"));

        return mapToResponseDto(reservationRepository.save(reservation));
    }
//    public ReservationResponseDto submitReview(String token, Integer reservationId, ReviewDto review) {
//        validateToken(token);
//
//        if (review.rating() < 1 || review.rating() > 5) {
//            throw new ArtisaException(HttpStatus.BAD_REQUEST,
//                    "Rating must be between 1 and 5");
//        }
//
//        Reservation reservation = reservationRepository.findById(reservationId)
//                .orElseThrow(() -> new ArtisaException(HttpStatus.NOT_FOUND, "Reservation not found"));
//
//        // Verify the status
//        if (!"COMPLETED".equals(reservation.getStatus())) {
//            throw new ArtisaException(HttpStatus.BAD_REQUEST,
//                    "Only completed reservations can be reviewed");
//        }
//
//        // Save review (you might want to create a separate Review entity)
//        // For now, we'll just update the notes
//        reservation.setNotes("Rating: " + review.rating() + " stars. " + review.comment());
//
//        return mapToResponseDto(reservationRepository.save(reservation));
//    }


    @Override
    public List<ReviewDto> getArtisanCompletedReviewedReservations(String token, Integer artisanId) {
        validateToken(token);

        // Fetch reviews for completed reservations of this artisan
        return reviewRepository.findByReviewedArtisanId(artisanId).stream()
                .map(review -> new ReviewDto(
                        review.getId(),
                        review.getReservation().getId(),
                        review.getRating(),
                        review.getComment(),
                        review.getReviewDate(),
                        review.getReviewer().getId(),
                        review.getReviewedArtisan().getId(),
                        review.getReviewer().getNomComplet(),
                        review.getReviewedArtisan().getNomComplet()
                ))
                .collect(Collectors.toList());
    }

    // Optional: Method to get reviews for an artisan
    @Override
    public List<ReviewDto> getArtisanReviews(String token, Integer artisanId) {
        validateToken(token);

        return reviewRepository.findByReviewedArtisanId(artisanId).stream()
                .map(review -> new ReviewDto(
                        review.getId(),
                        review.getReservation().getId(),
                        review.getRating(),
                        review.getComment(),
                        review.getReviewDate(),
                        review.getReviewer().getId(),
                        review.getReviewedArtisan().getId(),
                        review.getReviewer().getNomComplet(),
                        review.getReviewedArtisan().getNomComplet()
                ))
                .collect(Collectors.toList());
    }

    // Optional: Method to calculate artisan's average rating
    @Override
    public double getArtisanAverageRating(String token, Integer artisanId) {
        validateToken(token);

        return reviewRepository.findAverageRatingByReviewedArtisanId(artisanId);
    }

    private CompletionConfirmationDto mapToCompletionConfirmationDto(
            Reservation reservation,
            boolean bothConfirmed
    ) {
        return new CompletionConfirmationDto(
                reservation.getId(),
                reservation.getClient().getNomComplet(),
                reservation.getArtisan().getNomComplet(),
                reservation.getService().getTitre(),
                reservation.getService().getTarif(),
                reservation.getStatus(),
                reservation.getDateCreation(),
                reservation.getDateModification(),
                reservation.getProposedCompletionDate(),
                reservation.getNotes(),
                reservation.getArtisanConfirmed(),
                reservation.getClientConfirmed(),
                bothConfirmed
        );
    }


}