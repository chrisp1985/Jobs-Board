package com.chrisp1985.jobsboard_backend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtParser;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;

@Component
public class JwtUtils {

    // Should be stored externally in normal circumstances.
    private final String SECRET = "bXlTZWNyZXRLZXktMTIzNDU2Nzg5MDEyMzQ1Njc4OTAxMg==";

    private Key signingKey;
    private JwtParser jwtParser;

    @PostConstruct
    public void init() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET);
        signingKey = Keys.hmacShaKeyFor(decodedKey);


        jwtParser = Jwts
                .parserBuilder()
                .setSigningKey(signingKey)
                .build();
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return jwtParser
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername());
    }
}

