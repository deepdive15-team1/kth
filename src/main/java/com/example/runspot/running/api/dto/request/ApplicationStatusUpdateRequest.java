package com.example.runspot.running.api.dto.request;

import com.example.runspot.running.domain.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationStatusUpdateRequest {

    @NotNull(message = "변경할 상태를 입력해주세요.")
    private ApplicationStatus status;
}
