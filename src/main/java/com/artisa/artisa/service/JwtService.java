//package com.artisa.artisa.service;
//
//import com.artisa.artisa.entity.Utilisateur;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import java.security.Key;
//
//import javax.crypto.SecretKey;
//import java.nio.charset.StandardCharsets;
//import java.util.Date;
//import java.util.Map;
//
//@Service
//public class JwtService {
//
////    private static final String SECRET = "your-fixed-secret-key-here";
////    private static final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
//
//    // current solution
//    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
//    // current solution
//
//
////    private static final String secretKey = "artisa-secret";
////
////    public Key getSecretKey(){
////        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
////        return Keys.hmacShaKeyFor(keyBytes);
////    }
//
//    private static final long EXPIRATION_TIME = 3600000;
//
//    public String generateToken(Utilisateur user) {
//        Map<String, Object> claims = Map.of(
//                "id", user.getId(),
//                "nomComplet", user.getNomComplet(),
//                "email", user.getEmail(),
//                "adresse", user.getAdresse(),
//                "phone", user.getPhone(),
//                "roles", user.getRoles().stream().map(role -> role.getName()).toArray()
//        );
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(user.getNomComplet())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//                .signWith(secretKey)
////                .signWith(getSecretKey(),SignatureAlgorithm.HS512)
//                .compact();
//    }
//
//
//    public Claims getClaimsFromToken(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(secretKey)
////                .setSigningKey(getSecretKey())
//                .build()
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
//
//    public Integer getUserIdFromToken(String token) {
//        return (Integer) getClaimsFromToken(token).get("id");
//    }
//
//    public String[] getUserRolesFromToken(String token) {
//        return (String[]) getClaimsFromToken(token).get("roles");
//    }
//
//
////import com.artisa.artisa.entity.Utilisateur;
////import io.jsonwebtoken.Claims;
////import io.jsonwebtoken.Jwts;
////import io.jsonwebtoken.SignatureAlgorithm;
////import io.jsonwebtoken.security.Keys;
////import org.springframework.beans.factory.annotation.Value;
////import org.springframework.stereotype.Service;
////
////import javax.crypto.SecretKey;
////import java.nio.charset.StandardCharsets;
////import java.util.Date;
////import java.util.Map;
////
////@Service
////public class JwtService {
////
////    @Value("${jwt.secret}")
////    private String secret;
////
////    private SecretKey getSigningKey() {
////        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
////    }
////
////    private static final long EXPIRATION_TIME = 3600000;
////
////    public String generateToken(Utilisateur user) {
////        Map<String, Object> claims = Map.of(
////                "id", user.getId(),
////                "nomComplet", user.getNomComplet(),
////                "email", user.getEmail(),
////                "adresse", user.getAdresse(),
////                "phone", user.getPhone(),
////                "roles", user.getRoles().stream().map(role -> role.getName()).toArray()
////        );
////        return Jwts.builder()
////                .setClaims(claims)
////                .setSubject(user.getNomComplet())
////                .setIssuedAt(new Date())
////                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
////                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
////                .compact();
////    }
////
////    public Claims getClaimsFromToken(String token) {
////        return Jwts.parserBuilder()
////                .setSigningKey(getSigningKey())
////                .build()
////                .parseClaimsJws(token)
////                .getBody();
////    }
////
////    public boolean isTokenValid(String token) {
////        try {
////            Claims claims = getClaimsFromToken(token);
////            return !claims.getExpiration().before(new Date());
////        } catch (Exception e) {
////            return false;
////        }
////    }
////
////    public String getUsernameFromToken(String token) {
////        return getClaimsFromToken(token).getSubject();
////    }
////
////    public Integer getUserIdFromToken(String token) {
////        return (Integer) getClaimsFromToken(token).get("id");
////    }
////
////    public String[] getUserRolesFromToken(String token) {
////        return (String[]) getClaimsFromToken(token).get("roles");
////    }
////}
//
//
//}

package com.artisa.artisa.service;

import com.artisa.artisa.entity.Utilisateur;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    // Use a long, secure secret string
    private static final String JWT_SECRET =
            "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24 hours

    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

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
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET.getBytes())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenValid(String token) {
        try {
            String actualToken = token;
            if (token != null && token.startsWith("Bearer ")) {
                actualToken = token.substring(7);
            }
            extractAllClaims(actualToken);
            return !isTokenExpired(actualToken);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        String actualToken = token;
        if (token != null && token.startsWith("Bearer ")) {
            actualToken = token.substring(7);
        }

        return Jwts.parser()
                .setSigningKey(JWT_SECRET.getBytes())
                .parseClaimsJws(actualToken)
                .getBody();
    }

    public Integer getUserIdFromToken(String token) {
        return extractClaim(token, claims -> (Integer) claims.get("id"));
    }

    public String[] getUserRolesFromToken(String token) {
        return extractClaim(token, claims -> (String[]) claims.get("roles"));
    }
}