package com.artisa.artisa.dto;

import java.time.LocalDateTime;

public record ReviewDto(
        Integer id,
        Integer reservationId,
        Integer rating,
        String comment,
        LocalDateTime reviewDate,
        Integer reviewerId,
        Integer reviewedArtisanId,
        String reviewerName,
        String artisanName
) {
    // Add a constructor that allows creating a DTO with minimal required fields
    public ReviewDto(Integer reservationId, Integer rating, String comment) {
        this(
                null,           // id
                reservationId,  // reservationId
                rating,         // rating
                comment,        // comment
                LocalDateTime.now(), // reviewDate
                null,           // reviewerId
                null,           // reviewedArtisanId
                null,           // reviewerName
                null            // artisanName
        );
    }
}