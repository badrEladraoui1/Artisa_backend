package com.artisa.artisa.repository;

import com.artisa.artisa.entity.Transaction;
import com.artisa.artisa.enums.StatusTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByReservationId(Integer reservationId);
    Optional<Transaction> findByReservationIdAndStatus(Integer reservationId, StatusTransaction status);
}
