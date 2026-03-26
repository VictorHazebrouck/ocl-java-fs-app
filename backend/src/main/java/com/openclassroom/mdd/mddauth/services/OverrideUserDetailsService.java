package com.openclassroom.mdd.mddauth.services;

import com.openclassroom.mdd.mddauth.exceptions.AuthExceptions;
import com.openclassroom.mdd.mddauth.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OverrideUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
        throws RuntimeException {
        return userRepository
            .getByUsername(username)
            .orElseThrow(() -> new AuthExceptions.UserNotFound());
    }
}
