package com.artisa.artisa.repository;

import com.artisa.artisa.entity.Reservation;
import com.artisa.artisa.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByClientId(Integer clientId);
    List<Reservation> findByArtisanId(Integer artisanId);
    List<Reservation> findByClientIdAndStatus(Integer clientId, String status);
    List<Reservation> findByArtisanIdAndStatus(Integer artisanId, String status);
}