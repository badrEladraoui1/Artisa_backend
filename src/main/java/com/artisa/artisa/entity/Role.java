package com.artisa.artisa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<Utilisateur> users;

//    @ManyToMany(mappedBy = "roles")
//    private Set<Client> clients;
//
//    @ManyToMany(mappedBy = "roles")
//    private Set<Artisan> artisans;
}
