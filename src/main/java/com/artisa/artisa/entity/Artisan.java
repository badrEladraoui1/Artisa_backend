package com.artisa.artisa.entity;

import com.artisa.artisa.enums.MetierCategories;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
//@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "artisan")
public class Artisan extends Utilisateur {
    @OneToMany(mappedBy = "artisan")
    private List<Avis> evaluations;

    private String experience;
    @Enumerated(EnumType.STRING)
    @Column(name = "metier_categorie")
    private MetierCategories metier;
    private String portfolio;
}