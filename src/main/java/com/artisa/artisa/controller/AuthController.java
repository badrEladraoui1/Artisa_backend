package com.artisa.artisa.controller;

import com.artisa.artisa.dto.JwtAuthResponse;
import com.artisa.artisa.dto.LoginDto;
import com.artisa.artisa.dto.SignUpDto;
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

    @PostMapping("/signup/{role}")
    public ResponseEntity<String> signup(@Valid @RequestBody SignUpDto signUpDto, @PathVariable String role) {
        String response = authService.signup(signUpDto, role);
        return ResponseEntity.ok(response);
    }
}
