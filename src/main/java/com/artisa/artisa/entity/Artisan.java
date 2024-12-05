package com.artisa.artisa.entity;

import com.artisa.artisa.enums.MetierCategories;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "artisan")
public class Artisan extends Utilisateur {
    @OneToMany(mappedBy = "artisan")
    private List<Avis> evaluations;

    private String experience;
//    @Enumerated(EnumType.STRING)
    @Column(name = "metier",nullable = false)
//    private MetierCategories metier;
    private String metier;

    private String portfolio;

    @Column(name = "profile_picture_file_name")
    private String profilePictureFileName;

    @Size(max = 100, message = "Description must not exceed 100 characters.")
    private String description;
}