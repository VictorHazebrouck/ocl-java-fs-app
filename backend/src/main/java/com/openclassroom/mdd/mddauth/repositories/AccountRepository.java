package com.openclassroom.mdd.mddauth.repositories;

import com.openclassroom.mdd.mddauth.entities.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> getByUserIdAndProviderId(
        Long userId,
        Account.ProviderId providerId
    );
}
