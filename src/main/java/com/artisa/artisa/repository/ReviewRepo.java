package com.artisa.artisa.repository;

import com.artisa.artisa.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Integer> {
    List<Review> findByReviewedArtisanId(Integer artisanId);
    List<Review> findByReviewerId(Integer clientId);
    List<Review> findByReservationId(Integer reservationId);
    double findAverageRatingByReviewedArtisanId(Integer artisanId);
    boolean existsByReservationId(Integer reservationId);
}