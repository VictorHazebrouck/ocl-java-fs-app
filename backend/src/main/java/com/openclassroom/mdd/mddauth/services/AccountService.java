package com.openclassroom.mdd.mddauth.services;

import com.openclassroom.mdd.mddauth.entities.Account;
import com.openclassroom.mdd.mddauth.entities.User;
import com.openclassroom.mdd.mddauth.exceptions.AuthExceptions;
import com.openclassroom.mdd.mddauth.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    public Account createAccountPassword(User user, String rawPassword)
        throws RuntimeException {
        Account account = new Account();
        account.setUser(user);
        account.setProviderId(Account.ProviderId.PASSWORD);
        account.setPassword(passwordEncoder.encode(rawPassword));
        return accountRepository.save(account);
    }

    public Account createAccountGoogle(User user, String googleUserId)
        throws RuntimeException {
        Account account = new Account();
        account.setUser(user);
        account.setProviderId(Account.ProviderId.GOOGLE);
        account.setSocialLoginUserId(googleUserId);
        return accountRepository.save(account);
    }

    public Account loginPassword(User user, String password)
        throws RuntimeException {
        Account account = accountRepository
            .getByUserIdAndProviderId(user.getId(), Account.ProviderId.PASSWORD)
            .orElseThrow(() -> new AuthExceptions.AccountNotFound());

        if (!passwordEncoder.matches(password, account.getPassword())) {
            throw new AuthExceptions.WrongPassword();
        }

        return account;
    }

    public Account loginGoogle(User user, String googleUserId)
        throws RuntimeException {
        Account account = accountRepository
            .getByUserIdAndProviderId(user.getId(), Account.ProviderId.GOOGLE)
            .orElseThrow(() -> new AuthExceptions.AccountNotFound());

        return account;
    }

    public Account setAccountPasword(User user, String newPassword)
        throws RuntimeException {
        Account account = accountRepository
            .getByUserIdAndProviderId(user.getId(), Account.ProviderId.PASSWORD)
            .orElseThrow();

        account.setPassword(newPassword);
        return accountRepository.save(account);
    }
}
