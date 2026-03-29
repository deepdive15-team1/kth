package com.example.runspot.auth.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenRefreshRequest {

    @NotBlank(message = "리프레시 토큰을 입력해주세요.")
    private String refreshToken;
}
