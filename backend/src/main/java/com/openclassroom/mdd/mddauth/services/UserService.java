package com.openclassroom.mdd.mddauth.services;

import com.openclassroom.mdd.mddauth.entities.User;
import com.openclassroom.mdd.mddauth.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User getUserByUsernameOrEmail(String usernameOrEmail)
        throws RuntimeException {
        return userRepository
            .getByUsernameOrEmail(usernameOrEmail)
            .orElseThrow(() -> new EntityNotFoundException());
    }

    public boolean userAlreadyExistsByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.getByUsernameOrEmail(usernameOrEmail).isPresent();
    }

    public User createUser(String username, String email)
        throws RuntimeException {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        return userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public User setUsername(User user, String newUsername) {
        user.setUsername(newUsername);
        return userRepository.save(user);
    }

    public User getUser(User user) {
        return userRepository.getReferenceById(user.getId());
    }
}
