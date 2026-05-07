package com.openclassroom.mdd.mddauth.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.openclassroom.mdd.mddauth.entities.Account;
import com.openclassroom.mdd.mddauth.entities.User;
import com.openclassroom.mdd.mddauth.repositories.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

class AccountServiceTest {

    private PasswordEncoder passwordEncoder;
    private AccountRepository accountRepository;

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        passwordEncoder = mock(PasswordEncoder.class);
        accountRepository = mock(AccountRepository.class);

        accountService = new AccountService(passwordEncoder, accountRepository);
    }

    @Test
    void shouldCreatePasswordAccount() {
        User user = new User();
        user.setId(1L);

        when(passwordEncoder.encode("pass")).thenReturn("hashed-pass");

        when(accountRepository.save(any(Account.class))).thenAnswer(
            invocation -> invocation.getArgument(0)
        );

        Account result = accountService.createAccountPassword(user, "pass");

        assertEquals(Account.ProviderId.PASSWORD, result.getProviderId());
        assertEquals("hashed-pass", result.getPassword());
        assertEquals(user, result.getUser());

        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void shouldCreateGoogleAccount() {
        User user = new User();
        user.setId(1L);

        when(accountRepository.save(any(Account.class))).thenAnswer(
            invocation -> invocation.getArgument(0)
        );

        Account result = accountService.createAccountGoogle(user, "google-123");

        assertEquals(Account.ProviderId.GOOGLE, result.getProviderId());
        assertEquals("google-123", result.getSocialLoginUserId());
        assertEquals(user, result.getUser());

        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void shouldLoginPassword_success() {
        User user = new User();
        user.setId(1L);

        Account account = new Account();
        account.setPassword("hashed-pass");

        when(
            accountRepository.getByUserIdAndProviderId(
                1L,
                Account.ProviderId.PASSWORD
            )
        ).thenReturn(Optional.of(account));

        when(passwordEncoder.matches("raw-pass", "hashed-pass")).thenReturn(
            true
        );

        Account result = accountService.loginPassword(user, "raw-pass");

        assertNotNull(result);
        verify(passwordEncoder).matches("raw-pass", "hashed-pass");
    }

    @Test
    void shouldThrow_whenAccountNotFound_passwordLogin() {
        User user = new User();
        user.setId(1L);

        when(
            accountRepository.getByUserIdAndProviderId(
                1L,
                Account.ProviderId.PASSWORD
            )
        ).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
            accountService.loginPassword(user, "pass")
        );
    }

    @Test
    void shouldThrow_whenPasswordIncorrect() {
        User user = new User();
        user.setId(1L);

        Account account = new Account();
        account.setPassword("hashed-pass");

        when(
            accountRepository.getByUserIdAndProviderId(
                1L,
                Account.ProviderId.PASSWORD
            )
        ).thenReturn(Optional.of(account));

        when(passwordEncoder.matches("wrong", "hashed-pass")).thenReturn(false);

        assertThrows(BadCredentialsException.class, () ->
            accountService.loginPassword(user, "wrong")
        );
    }

    @Test
    void shouldLoginGoogle_success() {
        User user = new User();
        user.setId(1L);

        Account account = new Account();

        when(
            accountRepository.getByUserIdAndProviderId(
                1L,
                Account.ProviderId.GOOGLE
            )
        ).thenReturn(Optional.of(account));

        Account result = accountService.loginGoogle(user, "google-id");

        assertNotNull(result);
    }

    @Test
    void shouldSetPassword() {
        User user = new User();
        user.setId(1L);

        Account account = new Account();
        account.setPassword("old");

        when(
            accountRepository.getByUserIdAndProviderId(
                1L,
                Account.ProviderId.PASSWORD
            )
        ).thenReturn(Optional.of(account));

        when(accountRepository.save(any(Account.class))).thenAnswer(
            invocation -> invocation.getArgument(0)
        );

        Account result = accountService.setAccountPasword(user, "new-pass");

        assertEquals("new-pass", result.getPassword());
        verify(accountRepository).save(account);
    }

    @Test
    void shouldThrow_whenSetPasswordAccountMissing() {
        User user = new User();
        user.setId(1L);

        when(
            accountRepository.getByUserIdAndProviderId(
                1L,
                Account.ProviderId.PASSWORD
            )
        ).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
            accountService.setAccountPasword(user, "new")
        );
    }
}
