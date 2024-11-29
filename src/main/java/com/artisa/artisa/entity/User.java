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
//@Table(name = "user")
//
//// i ll use this instead if i didnt know how to set jwy with tree classes
//
//public class User {
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
//}
