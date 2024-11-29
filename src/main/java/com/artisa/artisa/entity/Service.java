package com.artisa.artisa.entity;

import com.artisa.artisa.enums.MetierCategories;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private MetierCategories categorie;
    @Column(name = "date_creation")
    private Date dateCreation;
    private String description;
    private double tarif;
    private String titre;

    @ManyToOne
    private Artisan artisan;
}