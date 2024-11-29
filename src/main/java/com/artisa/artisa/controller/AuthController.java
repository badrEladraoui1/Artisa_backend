package com.artisa.artisa.controller;

import com.artisa.artisa.dto.JwtAuthResponse;
import com.artisa.artisa.dto.LoginDto;
import com.artisa.artisa.dto.SignUpDto;
import com.artisa.artisa.service.AuthService;
import com.artisa.artisa.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")  // Add this to handle potential CORS issues
public class AuthController {

    private AuthenticationManager authenticationManager;
    private AuthService authService;
    private JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, AuthService authService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello";
    }

    @PostMapping("/test")
    public String test(){
        return "Test";
    }

    // ! i should add the case for client and artisan based on the uri path
//    public String login(@RequestParam String username, @RequestParam String password) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(username, password));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        return jwtService.generateToken(userDetails.getUsername());
//         this returns the users token after login
//    }

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
