package com.openclassroom.mdd.mddauth.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.openclassroom.mdd.mddauth.entities.User;
import com.openclassroom.mdd.mddauth.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OverrideUserDetailsServiceTest {

    private UserRepository userRepository;
    private OverrideUserDetailsService service;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        service = new OverrideUserDetailsService(userRepository);
    }

    @Test
    void shouldReturnUserDetails_whenUserExists() {
        User user = new User();
        user.setUsername("john");

        when(userRepository.getByUsername("john")).thenReturn(
            Optional.of(user)
        );

        var result = service.loadUserByUsername("john");

        assertNotNull(result);
        assertEquals("john", result.getUsername());
        verify(userRepository).getByUsername("john");
    }

    @Test
    void shouldThrowException_whenUserNotFound() {
        when(userRepository.getByUsername("missing")).thenReturn(
            Optional.empty()
        );

        assertThrows(EntityNotFoundException.class, () ->
            service.loadUserByUsername("missing")
        );

        verify(userRepository).getByUsername("missing");
    }
}
