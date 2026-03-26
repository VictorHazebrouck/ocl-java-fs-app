package com.openclassroom.mdd.mddauth.repositories;

import com.openclassroom.mdd.mddauth.entities.Session;
import com.openclassroom.mdd.mddauth.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> getByTokenHash(String tokenHash);

    void deleteByUserAndIpAddressAndUserAgent(
        User user,
        String ipAddress,
        String userAgent
    );
}
