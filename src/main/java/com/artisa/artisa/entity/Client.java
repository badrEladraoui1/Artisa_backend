package com.artisa.artisa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client")
public class Client extends Utilisateur {
    @ToString.Exclude
    @OneToMany(mappedBy = "client")

    @Column(name = "historique_reservations")
    private List<Reservation> historiqueReservations;

    @Column(name = "profile_picture_file_name")
    private String profilePictureFileName;
}
