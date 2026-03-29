package com.example.runspot.running.api.dto.response;

import com.example.runspot.running.domain.ApplicationStatus;
import com.example.runspot.running.domain.entity.RunningApplication;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RunningApplicationResponse {
    private Long id;
    private Long runningPostId;
    private Long applicantUserId;
    private String messageToHost;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;
    private LocalDateTime decidedAt;

    public static RunningApplicationResponse from(RunningApplication application) {
        return new RunningApplicationResponse(
                application.getId(),
                application.getRunningPost().getId(),
                application.getApplicantUser().getId(),
                application.getMessageToHost(),
                application.getStatus(),
                application.getAppliedAt(),
                application.getDecidedAt()
        );
    }
}
