package com.mrbonk97.authstarter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE account SET deleted_at = NOW() WHERE id=?")
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Date emailVerified;
    private String image;
    @Enumerated(EnumType.STRING)
    private Provider provider;
    private String providerId;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    @Transient private String accessToken;
    @Transient private String refreshToken;

    @PrePersist
    void setCreateAt() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    @PreUpdate
    void setUpdatedAt() {
        this.updatedAt = new Date();
    }

}
