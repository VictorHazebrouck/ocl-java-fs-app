package com.openclassroom.mdd.mddauth.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.openclassroom.mdd.mddauth.entities.User;
import com.openclassroom.mdd.mddauth.exceptions.AuthExceptions;
import com.openclassroom.mdd.mddauth.repositories.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void should_return_user_by_username_or_email() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john");

        when(userRepository.getByUsernameOrEmail("john")).thenReturn(
            Optional.of(user)
        );

        User result = userService.getUserByUsernameOrEmail("john");

        assertEquals("john", result.getUsername());
        verify(userRepository).getByUsernameOrEmail("john");
    }

    @Test
    void should_throw_exception_when_user_not_found() {
        when(userRepository.getByUsernameOrEmail("unknown")).thenReturn(
            Optional.empty()
        );

        assertThrows(AuthExceptions.AccountNotFound.class, () ->
            userService.getUserByUsernameOrEmail("unknown")
        );
    }

    @Test
    void should_return_true_if_user_exists() {
        when(userRepository.getByUsernameOrEmail("john")).thenReturn(
            Optional.of(new User())
        );

        boolean result = userService.userAlreadyExistsByUsernameOrEmail("john");

        assertTrue(result);
    }

    @Test
    void should_return_false_if_user_does_not_exist() {
        when(userRepository.getByUsernameOrEmail("john")).thenReturn(
            Optional.empty()
        );

        boolean result = userService.userAlreadyExistsByUsernameOrEmail("john");

        assertFalse(result);
    }

    @Test
    void should_create_user() {
        User savedUser = new User();
        savedUser.setUsername("john");
        savedUser.setEmail("john@mail.com");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.createUser("john", "john@mail.com");

        assertEquals("john", result.getUsername());
        assertEquals("john@mail.com", result.getEmail());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void should_delete_user() {
        User user = new User();
        user.setId(1L);

        userService.deleteUser(user);

        verify(userRepository).delete(user);
    }

    @Test
    void should_update_username() {
        User user = new User();
        user.setId(1L);
        user.setUsername("old");

        when(userRepository.save(any(User.class))).thenAnswer(invocation ->
            invocation.getArgument(0)
        );

        User result = userService.setUsername(user, "new");

        assertEquals("new", result.getUsername());
        verify(userRepository).save(user);
    }

    @Test
    void should_get_user_by_reference() {
        User user = new User();
        user.setId(1L);

        when(userRepository.getReferenceById(1L)).thenReturn(user);

        User result = userService.getUser(user);

        assertEquals(1L, result.getId());
        verify(userRepository).getReferenceById(1L);
    }
}
