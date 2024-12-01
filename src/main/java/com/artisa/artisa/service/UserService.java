package com.artisa.artisa.service;

import com.artisa.artisa.entity.*;
import com.artisa.artisa.repository.RoleRepo;
import com.artisa.artisa.repository.UtilisateurRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UtilisateurRepo userRepository;

    @Autowired
    private RoleRepo roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

// i did this just for populating the first users in the database now is commented

//    @PostConstruct
//    public void init() {
//        // Create roles
//        Role adminRole = new Role();
//        adminxwRole.setName("ADMIN");
//        roleRepository.save(adminRole);
//
//        Role clientRole = new Role();
//        clientRole.setName("CLIENT");
//        roleRepository.save(clientRole);
//
//        Role artisanRole = new Role();
//        artisanRole.setName("ARTISAN");
//        roleRepository.save(artisanRole);
//
//        // Create users
//        Administrateur admin = new Administrateur();
//        admin.setNomComplet("admin");
//        admin.setUsername("adminUsername");
//        admin.setMotDePasse(passwordEncoder.encode("admin"));
//        admin.setRoles(new HashSet<>(Set.of(adminRole)));
//        userRepository.save(admin);
//
//        Client client = new Client();
//        client.setNomComplet("client");
//        client.setMotDePasse(passwordEncoder.encode("client"));
//        client.setRoles(new HashSet<>(Set.of(clientRole)));
//        userRepository.save(client);
//
//        Artisan artisan = new Artisan();
//        artisan.setNomComplet("artisan");
//        artisan.setMotDePasse(passwordEncoder.encode("artisan"));
//        artisan.setRoles(new HashSet<>(Set.of(artisanRole)));
//        userRepository.save(artisan);
//    }

}
