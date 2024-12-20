package com.artisa.artisa.entity;

import com.artisa.artisa.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "artisan_id", nullable = false)
    private Artisan artisan;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private ReservationStatus status = ReservationStatus.PENDING;

    @Column(nullable = false)
    private String status = "PENDING";  // Default value

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    private String notes;  // Optional notes from client or artisan

    @Column(nullable = false)
    private Double montant; // Add this field

    @Column(name = "proposed_completion_date")
    private LocalDateTime proposedCompletionDate;

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
        dateModification = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dateModification = LocalDateTime.now();
    }
}