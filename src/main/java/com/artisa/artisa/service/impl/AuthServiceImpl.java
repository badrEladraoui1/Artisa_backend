package com.artisa.artisa.service.impl;

import com.artisa.artisa.dto.LoginDto;
import com.artisa.artisa.dto.SignUpDto;
import com.artisa.artisa.entity.Artisan;
import com.artisa.artisa.entity.Client;
import com.artisa.artisa.entity.Role;
import com.artisa.artisa.entity.Utilisateur;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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

//    @Override
//    public String login(LoginDto loginDto) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginDto.nomComplet(), loginDto.motDePasse()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        return jwtService.generateToken(userDetails.getUsername());
//        // returns the token
//    }

    @Override
    public String login(LoginDto loginDto) {
        try {
            // Log the received login details (but not the password for security)
            System.out.println("Attempting login for user: " + loginDto.nomComplet());

            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.nomComplet(),
                            loginDto.motDePasse()
                    )
            );

            // Log successful authentication
            System.out.println("Authentication successful for user: " + loginDto.nomComplet());

            // Set the authentication in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Extract UserDetails from authentication
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println("UserDetails retrieved: " + userDetails.getUsername());

            // Generate the JWT token
            String token = jwtService.generateToken(userDetails.getUsername());
            System.out.println("Generated JWT token: " + token);

            // Return the token as a response
            return token;

        } catch (Exception e) {
            // Log and rethrow the exception
            System.err.println("Error during login: " + e.getMessage());
            e.printStackTrace();

            // You can throw a custom exception if needed
            throw new RuntimeException("Login failed: " + e.getMessage());
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
            Role userRole = roleRepository.findByName("ROLE_" + role.toUpperCase()).get();
            roles.add(userRole);
            artisan.setRoles(roles);

            utilisateurRepo.save(artisan);

        } else if (role.equals("client")){
            Client client = new Client();
            client.setNomComplet(signUpDto.nomComplet());
            client.setEmail(signUpDto.email());
            client.setAdresse(signUpDto.address());
            client.setPhone(signUpDto.phone());
            client.setMotDePasse(passwordEncoder.encode(signUpDto.motDePasse()));

            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName("ROLE_" + role.toUpperCase()).get();
            roles.add(userRole);
            client.setRoles(roles);

            utilisateurRepo.save(client);

        }else {
            throw new ArtisaException(HttpStatus.BAD_REQUEST, "Role is not valid!.");
        }

        return role + " registered successfully!.";
    }
}
