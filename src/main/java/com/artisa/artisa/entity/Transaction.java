package com.artisa.artisa.entity;

import com.artisa.artisa.enums.StatusTransaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date date;
    private double montant;
    @Enumerated(EnumType.STRING)
    private StatusTransaction status;
}