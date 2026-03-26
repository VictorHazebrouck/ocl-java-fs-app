package com.openclassroom.mdd.mddauth.services;

import com.openclassroom.mdd.mddauth.dtos.UserDto;
import com.openclassroom.mdd.mddauth.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TokenService {

    private final String key = "01234567890123456789012345678901";
    private final SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes());

    public String generateAccessToken(User user) {
        return Jwts.builder()
            .subject(user.getId().toString())
            .claim("id", user.getId())
            .claim("username", user.getUsername())
            .claim("email", user.getEmail())
            .claim("emailVerified", user.getEmailVerified())
            .claim("createdAt", user.getCreatedAt().toString())
            .claim("updatedAt", user.getUpdatedAt().toString())
            .issuedAt(new Date())
            .expiration(Date.from(Instant.now().plusSeconds(60 * 15)))
            .signWith(secretKey)
            .compact();
    }

    public UserDto decodeAccessToken(String accessToken) {
        Claims claims = Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(accessToken)
            .getPayload();

        return new UserDto(
            claims.get("id", Long.class),
            claims.get("username", String.class),
            claims.get("email", String.class),
            claims.get("emailVerified", Boolean.class),
            LocalDateTime.parse(claims.get("createdAt", String.class)),
            LocalDateTime.parse(claims.get("updatedAt", String.class))
        );
    }

    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    public String hashRefreshToken(String refreshToken) {
        return refreshToken + "_HASHED";
    }
}
