package com.artisa.artisa.service.impl;

import com.artisa.artisa.dto.LoginDto;
import com.artisa.artisa.dto.SignUpDto;
import com.artisa.artisa.entity.*;
import com.artisa.artisa.exception.ArtisaException;
import com.artisa.artisa.repository.RoleRepo;
import com.artisa.artisa.repository.UtilisateurRepo;
import com.artisa.artisa.service.AuthService;
import com.artisa.artisa.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    JwtService jwtService;
    AuthenticationManager authenticationManager;
    UtilisateurRepo utilisateurRepo;
    RoleRepo roleRepository;
    PasswordEncoder passwordEncoder;

    public AuthServiceImpl(JwtService jwtService, AuthenticationManager authenticationManager , RoleRepo roleRepo , PasswordEncoder passwordEncoder ,     UtilisateurRepo utilisateurRepo) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.utilisateurRepo = utilisateurRepo;
        this.roleRepository = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginDto loginDto) {
        Utilisateur user = utilisateurRepo.findByNomComplet(loginDto.nomComplet())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(loginDto.motDePasse(), user.getMotDePasse())) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.nomComplet(), loginDto.motDePasse()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return jwtService.generateToken(user);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }


    @Override
    public String signup(SignUpDto signUpDto, String role) {
        if (utilisateurRepo.existsByNomComplet(signUpDto.nomComplet())) {
            throw new ArtisaException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
        }

        if(role.equals("artisan")){
            Artisan artisan = new Artisan();

            artisan.setNomComplet(signUpDto.nomComplet());
            artisan.setEmail(signUpDto.email());
            artisan.setAdresse(signUpDto.address());
            artisan.setPhone(signUpDto.phone());
            artisan.setMotDePasse(passwordEncoder.encode(signUpDto.motDePasse()));

            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName(role.toUpperCase()).get();

            roles.add(userRole);
            artisan.setRoles(roles);

            utilisateurRepo.save(artisan);

        } else if (role.equals("client")) {
            Client client = new Client();

            client.setNomComplet(signUpDto.nomComplet());
            client.setEmail(signUpDto.email());
            client.setAdresse(signUpDto.address());
            client.setPhone(signUpDto.phone());
            client.setMotDePasse(passwordEncoder.encode(signUpDto.motDePasse()));

            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName(role.toUpperCase()).get();
            roles.add(userRole);
            client.setRoles(roles);

            utilisateurRepo.save(client);
        }else if (role.equals("admin")){
            Administrateur admin = new Administrateur();

            admin.setNomComplet(signUpDto.nomComplet());
            admin.setEmail(signUpDto.email());
            admin.setAdresse(signUpDto.address());
            admin.setMotDePasse(passwordEncoder.encode(signUpDto.motDePasse()));
            admin.setPhone(signUpDto.phone());

            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName(role.toUpperCase()).get();
            roles.add(userRole);
            admin.setRoles(roles);

            utilisateurRepo.save(admin);
        }else {
            throw new ArtisaException(HttpStatus.BAD_REQUEST, "Something went wrong !!!.");
        }
        return role + " registered successfully!.";
    }
}
