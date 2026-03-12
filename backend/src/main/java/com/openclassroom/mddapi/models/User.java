package com.openclassroom.mddapi.models;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(
    name = "users",
    uniqueConstraints = { @UniqueConstraint(columnNames = "email") }
)
@Data
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(of = { "id" })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    @Size(max = 50)
    @Email
    @Column(nullable = false)
    private String email;

    @Nonnull
    @Size(max = 20)
    @Column(nullable = false)
    private String lastName;

    @Nonnull
    @Size(max = 20)
    @Column(nullable = false)
    private String firstName;

    @Nonnull
    @Size(max = 120)
    @Column(nullable = false)
    private String password;

    @Nonnull
    private Boolean admin;

    @CreatedDate
    @Column(
        updatable = false,
        columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;
}
