package com.example.runspot.auth.service;

import com.example.runspot.auth.api.dto.request.LoginRequest;
import com.example.runspot.auth.api.dto.request.SignupRequest;
import com.example.runspot.auth.api.dto.response.TokenResponse;
import com.example.runspot.auth.domain.entity.RefreshToken;
import com.example.runspot.auth.domain.repository.RefreshTokenRepository;
import com.example.runspot.global.jwt.TokenProvider;
import com.example.runspot.user.domain.entity.User;
import com.example.runspot.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public void signup(SignupRequest request) {
        if (userRepository.existsByLoginId(request.getLoginId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        User user = User.create(
                request.getLoginId(),
                passwordEncoder.encode(request.getPassword()),
                request.getName(),
                request.getAgeGroup(),
                request.getWeeklyRunningCount(),
                request.getAveragePace()
        );

        userRepository.save(user);
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다.");
        }

        String accessToken = tokenProvider.createAccessToken(user.getId(), user.getRole().name());
        String refreshTokenString = tokenProvider.createRefreshToken();
        long refreshTokenValidityInSeconds = tokenProvider.getRefreshTokenValidityInSeconds();

        // 기존 리프레시 토큰이 있으면 업데이트, 없으면 새로 생성
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(user.getId())
                .orElse(RefreshToken.builder()
                        .user(user)
                        .refreshToken(refreshTokenString)
                        .expiresAt(LocalDateTime.now().plusSeconds(refreshTokenValidityInSeconds))
                        .build());

        refreshToken.updateToken(refreshTokenString, LocalDateTime.now().plusSeconds(refreshTokenValidityInSeconds));
        refreshTokenRepository.save(refreshToken);

        return new TokenResponse(accessToken, refreshTokenString);
    }

    @Transactional
    public void logout(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    @Transactional
    public TokenResponse refresh(String refreshTokenString) {
        if (!tokenProvider.validateToken(refreshTokenString)) {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }

        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(refreshTokenString)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리프레시 토큰입니다."));

        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("만료된 리프레시 토큰입니다.");
        }

        User user = refreshToken.getUser();
        String newAccessToken = tokenProvider.createAccessToken(user.getId(), user.getRole().name());
        String newRefreshTokenString = tokenProvider.createRefreshToken();
        long refreshTokenValidityInSeconds = tokenProvider.getRefreshTokenValidityInSeconds();

        refreshToken.updateToken(newRefreshTokenString, LocalDateTime.now().plusSeconds(refreshTokenValidityInSeconds));

        return new TokenResponse(newAccessToken, newRefreshTokenString);
    }
}
