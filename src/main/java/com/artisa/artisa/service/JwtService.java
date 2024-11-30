package com.artisa.artisa.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Secure key generation
    private static final long EXPIRATION_TIME = 3600000; // 1 hour in milliseconds

    // Generate Token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey) // Use the secure key for signing
                .compact();
    }


    // Extract Claims from Token
    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey) // Use the same key for validation
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Validate Token
    public boolean isTokenValid(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false; // Invalid token
        }
    }

    // Extract Username from Token
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }
}


//package com.artisa.artisa.service;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import lombok.Value;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.SecretKey;
//import java.util.Date;
//
//@Service
//public class JwtService {
//
////    @Value("${jwt.secret}")
//    private String secret = "MySecretKey";
//
////    @Value("${jwt.expiration}")
//    private long expiration = 3600000;
//
//    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Secure key generation
//
//
//    public String generateToken(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expiration))
////                .signWith(SignatureAlgorithm.HS512, secret)
//                .signWith(secretKey) // Use the secure key here
//                .compact();
//    }
//
//    public Claims getClaimsFromToken(String token) {
//        return Jwts
//                .parser()
//                .setSigningKey(secret)
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    public boolean isTokenValid(String token) {
//        try {
//            Claims claims = getClaimsFromToken(token);
//            return !claims.getExpiration().before(new Date());
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    public String getUsernameFromToken(String token) {
//        return getClaimsFromToken(token).getSubject();
//    }
//}
//
