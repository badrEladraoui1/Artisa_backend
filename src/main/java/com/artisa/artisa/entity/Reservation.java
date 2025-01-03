package com.artisa.artisa.entity;

import com.artisa.artisa.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "client_confirmed")
    private Boolean clientConfirmed = false;

    @Column(name = "artisan_confirmed")
    private Boolean artisanConfirmed = false;

    @Column(name = "completion_date")
    private LocalDateTime completionDate;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    // Method to add a review
    public void addReview(Review review) {
        reviews.add(review);
        review.setReservation(this);
    }

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