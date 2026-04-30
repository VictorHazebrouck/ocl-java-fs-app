package com.openclassroom.mdd.mddauth.services;

import static org.junit.jupiter.api.Assertions.*;

import com.openclassroom.mdd.mddauth.dtos.UserDto;
import com.openclassroom.mdd.mddauth.entities.User;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class TokenServiceTest {

    private final TokenService tokenService = new TokenService();

    @Test
    void should_generate_access_token() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john");
        user.setEmail("john@mail.com");
        user.setEmailVerified(true);
        user.setCreatedAt(LocalDateTime.now().minusDays(1));
        user.setUpdatedAt(LocalDateTime.now());

        String token = tokenService.generateAccessToken(user);

        assertNotNull(token);
        assertTrue(token.split("\\.").length == 3); // JWT structure
    }

    @Test
    void should_decode_access_token() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john");
        user.setEmail("john@mail.com");
        user.setEmailVerified(true);
        user.setCreatedAt(LocalDateTime.now().minusDays(1));
        user.setUpdatedAt(LocalDateTime.now());

        String token = tokenService.generateAccessToken(user);

        UserDto dto = tokenService.decodeAccessToken(token);

        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getUsername(), dto.getUsername());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getEmailVerified(), dto.getEmailVerified());
    }

    @Test
    void should_generate_refresh_token() {
        String refreshToken = tokenService.generateRefreshToken();

        assertNotNull(refreshToken);
        assertFalse(refreshToken.isBlank());
    }

    @Test
    void should_hash_refresh_token() {
        String raw = "token123";

        String hashed = tokenService.hashRefreshToken(raw);

        assertEquals("token123_HASHED", hashed);
    }
}
