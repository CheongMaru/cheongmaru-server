package com.cheongmaru.domain.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken {

    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;


    @Column(nullable = false, length = 512, columnDefinition = "TEXT")
    private String token;

    public RefreshToken(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public void update(String newToken) {
        this.token = newToken;
    }
}

