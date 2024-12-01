package com.artisa.artisa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "utilisateur", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "nom_complet")
})
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nom_complet", nullable = false)
    private String nomComplet;

    @Column(name = "adresse")
    @Size(max = 100, message = "Adresse must be less than or equal to 100 characters.")
    private String adresse;

    @Column(name = "date_inscription")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateInscription;

    @Column(name = "email", nullable = false)
    @Email(message = "Email should be valid and follow the format example@example.abc")
    private String email;

    @Column(name = "mot_de_passe", nullable = false)
    @NotBlank(message = "Mot de passe is required.")
    private String motDePasse;

    @Column(name = "phone", nullable = false)
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits.")
    private String phone;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @PrePersist
    protected void onCreate() {
        dateInscription = new Date();
    }
}
