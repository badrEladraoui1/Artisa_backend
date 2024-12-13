package com.artisa.artisa.entity;

import com.artisa.artisa.enums.MetierCategories;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

//    @Enumerated(EnumType.STRING)
    private String categorie;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

    private String description;

    @Column(name = "service_picture_file_name")
    private String servicePictureFileName;

    private double tarif;

    private String titre;

    @ManyToOne
    private Artisan artisan;

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
}