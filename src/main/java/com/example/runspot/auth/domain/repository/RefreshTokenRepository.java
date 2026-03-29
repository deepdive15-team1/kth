package com.example.runspot.auth.domain.repository;

import com.example.runspot.auth.domain.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    Optional<RefreshToken> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
