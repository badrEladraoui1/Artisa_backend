package com.artisa.artisa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "utilisateur")
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateur implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nom_complet")
    private String nomComplet;
    private String adresse;
    @Column(name = "date_inscription")
    private Date dateInscription;
    private String email;
    @Column(name = "mot_de_passe")
    private String motDePasse;
    private String phone;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    // UserDetails implementation
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert roles to GrantedAuthority
        return roles.stream()
                .map(role -> (GrantedAuthority) role::getName)
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return motDePasse;
    }

    @Override
    public String getUsername() {
        return nomComplet;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Adjust as needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Adjust as needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Adjust as needed
    }

    @Override
    public boolean isEnabled() {
        return true; // Adjust as needed
    }
}


//package com.artisa.artisa.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.Date;
//import java.util.Set;
//
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Builder
//@Table(name = "utilisateur")
//@Inheritance(strategy = InheritanceType.JOINED)
//public class Utilisateur {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Column(name = "nom_complet")
//    private String nomComplet;
//    private String adresse;
//    @Column(name = "date_inscription")
//    private Date dateInscription;
//    private String email;
//    @Column(name = "mot_de_passe")
//    private String motDePasse;
//    private String phone;
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "user_role",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id")
//    )
//    private Set<Role> roles;
////    private String role;
//}