package com.artisa.artisa.service;

import com.artisa.artisa.entity.Utilisateur;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final long EXPIRATION_TIME = 3600000;

    public String generateToken(Utilisateur user) {
        Map<String, Object> claims = Map.of(
                "id", user.getId(),
                "nomComplet", user.getNomComplet(),
                "email", user.getEmail(),
                "adresse", user.getAdresse(),
                "phone", user.getPhone(),
                "roles", user.getRoles().stream().map(role -> role.getName()).toArray()
        );
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getNomComplet())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }


    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }
}