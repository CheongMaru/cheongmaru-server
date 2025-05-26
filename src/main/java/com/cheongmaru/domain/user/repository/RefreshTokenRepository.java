package com.cheongmaru.domain.user.repository;

import com.cheongmaru.domain.user.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByUserId(String userId);
    void deleteByUserId(String userId);
}
