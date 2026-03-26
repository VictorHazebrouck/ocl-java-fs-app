package com.openclassroom.mdd.mddauth.repositories;

import com.openclassroom.mdd.mddauth.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getByEmail(String email);

    Optional<User> getByUsername(String username);

    @Query(
        """
        SELECT u FROM User u
        WHERE u.username = :usernameOrEmail OR u.email = :usernameOrEmail
        """
    )
    Optional<User> getByUsernameOrEmail(String usernameOrEmail);
}
