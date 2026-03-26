package com.openclassroom.mdd.mddauth.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@Table(
    name = "accounts",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = { "provider_id", "user_id" }),
    }
)
@EntityListeners(AuditingEntityListener.class)
public class Account {

    public enum ProviderId {
        PASSWORD,
        GOOGLE,
        GITHUB,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private ProviderId providerId = ProviderId.PASSWORD;

    @Nullable
    @Size(max = 120)
    @Column(nullable = true)
    // only if providerId == ProviderId.PASSWORD
    private String password;

    @Nullable
    @Size(max = 120)
    @Column(nullable = true)
    // only if providerId != ProviderId.PASSWORD
    private String socialLoginUserId;

    @CreatedDate
    @Column(
        nullable = false,
        updatable = false,
        columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(
        nullable = false,
        columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    // @AssertTrue(message = "Invalid account configuration")
    // public boolean isValid() {
    //     return switch (providerId) {
    //         case PASSWORD -> password != null && !password.isBlank();
    //         case GOOGLE, GITHUB -> socialLoginUserId != null &&
    //         !socialLoginUserId.isBlank();
    //     };
    // }
}
