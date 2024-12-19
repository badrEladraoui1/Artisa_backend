package com.artisa.artisa.controller;

import com.artisa.artisa.dto.*;
import com.artisa.artisa.service.AuthService;
import com.artisa.artisa.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, AuthService authService, JwtService jwtService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping("/signup/artisan")
    public ResponseEntity<String> signup(@Valid @ModelAttribute SignUpDtoArtisan signUpDto) {
        String response = authService.signup(signUpDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup/client")
    public ResponseEntity<String> signup(@Valid @ModelAttribute SignUpDtoClient signUpDto) {
        String response = authService.signup(signUpDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup/admin")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupDtoAdmin signUpDto) {
        String response = authService.signup(signUpDto);
        return ResponseEntity.ok(response);
    }
}
