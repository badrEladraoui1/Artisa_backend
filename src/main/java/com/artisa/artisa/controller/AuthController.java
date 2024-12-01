package com.artisa.artisa.controller;

import com.artisa.artisa.dto.JwtAuthResponse;
import com.artisa.artisa.dto.LoginDto;
import com.artisa.artisa.dto.SignUpDto;
import com.artisa.artisa.service.AuthService;
import com.artisa.artisa.service.JwtService;
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

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    @PostMapping("/test")
    public String test(){
        return "Test";
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping("/signup/{role}")
    public ResponseEntity<String> signup(@RequestBody SignUpDto signUpDto, @PathVariable String role) {
        String response = authService.signup(signUpDto, role);
        return ResponseEntity.ok(response);
    }
}
