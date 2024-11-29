package com.artisa.artisa.entity;


import com.artisa.artisa.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date date;
    private double montant;
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Service service;
}