package com.artisa.artisa.entity;

import com.artisa.artisa.entity.Reservation;
import com.artisa.artisa.enums.StatusTransaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// Transaction.java
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private double montant;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private StatusTransaction status;

    @Column(nullable = false)
    private String status;

    private String paymentMethod; // "CARD" or "PAYPAL"

    // For PayPal
    private String paypalEmail;

    // For Card (store last 4 digits only for reference)
    private String cardLastFour;
    private String cardExpiryDate;
}