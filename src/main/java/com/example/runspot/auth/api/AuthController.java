package com.example.runspot.auth.api;

import com.example.runspot.auth.api.dto.request.LoginRequest;
import com.example.runspot.auth.api.dto.request.SignupRequest;
import com.example.runspot.auth.api.dto.request.TokenRefreshRequest;
import com.example.runspot.auth.api.dto.response.TokenResponse;
import com.example.runspot.auth.service.AuthService;
import com.example.runspot.global.security.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        TokenResponse tokenResponse = authService.login(request);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        Long userId = SecurityUtil.getCurrentUserId();
        authService.logout(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@Valid @RequestBody TokenRefreshRequest request) {
        TokenResponse tokenResponse = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(tokenResponse);
    }
}
