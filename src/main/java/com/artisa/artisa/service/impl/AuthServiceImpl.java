package com.artisa.artisa.service.impl;

import com.artisa.artisa.dto.LoginDto;
import com.artisa.artisa.dto.SignUpDtoArtisan;
import com.artisa.artisa.dto.SignUpDtoClient;
import com.artisa.artisa.dto.SignupDtoAdmin;
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
        try {
            Utilisateur user = utilisateurRepo.findByNomComplet(loginDto.nomComplet())
                    .orElseThrow(() -> new ArtisaException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));

            if (!passwordEncoder.matches(loginDto.motDePasse(), user.getMotDePasse())) {
                throw new ArtisaException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.nomComplet(), loginDto.motDePasse()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return jwtService.generateToken(user);
        } catch (Exception e) {
            throw new ArtisaException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
    }


    @Override
    public String signup(SignUpDtoClient signUpDtoClient) {
        if (utilisateurRepo.existsByNomComplet(signUpDtoClient.nomComplet())) {
            throw new ArtisaException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
        }

        try {
            Client client = new Client();

            client.setNomComplet(signUpDtoClient.nomComplet());
            client.setEmail(signUpDtoClient.email());
            client.setAdresse(signUpDtoClient.address());
            client.setPhone(signUpDtoClient.phone());
            client.setMotDePasse(passwordEncoder.encode(signUpDtoClient.motDePasse()));

            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName("CLIENT").get();
            System.out.println("userRole = " + userRole);
            roles.add(userRole);
            client.setRoles(roles);

            utilisateurRepo.save(client);
        }catch (Exception e){
            throw new ArtisaException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return  " registered successfully!.";
    }

    @Override
    public String signup(SignupDtoAdmin signupDtoAdmin) {

        try {
            Administrateur admin = new Administrateur();

            admin.setNomComplet(signupDtoAdmin.nomComplet());
            admin.setEmail(signupDtoAdmin.email());
            admin.setAdresse(signupDtoAdmin.address());
            admin.setMotDePasse(passwordEncoder.encode(signupDtoAdmin.motDePasse()));
            admin.setPhone(signupDtoAdmin.phone());

            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName("ADMIN").get();
            roles.add(userRole);
            admin.setRoles(roles);

            utilisateurRepo.save(admin);
        }catch (Exception e){
            throw new ArtisaException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return  " registered successfully!.";
        }


    @Override
    public String signup(SignUpDtoArtisan signUpDtoArtisan) {
        if (utilisateurRepo.existsByNomComplet(signUpDtoArtisan.nomComplet())) {
            throw new ArtisaException(HttpStatus.BAD_REQUEST, "Username is already exists!.");
        }

        try {
            Artisan artisan = new Artisan();

            artisan.setNomComplet(signUpDtoArtisan.nomComplet());
            artisan.setEmail(signUpDtoArtisan.email());
            artisan.setAdresse(signUpDtoArtisan.address());
            artisan.setPhone(signUpDtoArtisan.phone());
            artisan.setMotDePasse(passwordEncoder.encode(signUpDtoArtisan.motDePasse()));
            artisan.setMetier(signUpDtoArtisan.metier());
            artisan.setDescription(signUpDtoArtisan.description());

            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName("ARTISAN").get();

            roles.add(userRole);
            artisan.setRoles(roles);

            utilisateurRepo.save(artisan);
        }catch (Exception e){
            throw new ArtisaException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return  " registered successfully!.";



//        } else if (role.equals("client")) {
//            Client client = new Client();
//
//            client.setNomComplet(signUpDto.nomComplet());
//            client.setEmail(signUpDto.email());
//            client.setAdresse(signUpDto.address());
//            client.setPhone(signUpDto.phone());
//            client.setMotDePasse(passwordEncoder.encode(signUpDto.motDePasse()));
//
//            Set<Role> roles = new HashSet<>();
//            Role userRole = roleRepository.findByName(role.toUpperCase()).get();
//            roles.add(userRole);
//            client.setRoles(roles);
//
//            utilisateurRepo.save(client);
//        }

//            else if (role.equals("admin")){
//            Administrateur admin = new Administrateur();
//
//            admin.setNomComplet(signUpDto.nomComplet());
//            admin.setEmail(signUpDto.email());
//            admin.setAdresse(signUpDto.address());
//            admin.setMotDePasse(passwordEncoder.encode(signUpDto.motDePasse()));
//            admin.setPhone(signUpDto.phone());
//
//            Set<Role> roles = new HashSet<>();
//            Role userRole = roleRepository.findByName(role.toUpperCase()).get();
//            roles.add(userRole);
//            admin.setRoles(roles);
//
//            utilisateurRepo.save(admin);
//        }
//        }else {
//            throw new ArtisaException(HttpStatus.BAD_REQUEST, "Something went wrong !!!.");
//        }
//        return role + " registered successfully!.";
    }
}
