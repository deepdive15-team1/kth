package com.example.runspot.running.api.dto.response;

import com.example.runspot.running.domain.GenderPolicy;
import com.example.runspot.running.domain.RunningStatus;
import com.example.runspot.running.domain.RunningType;
import com.example.runspot.running.domain.entity.RunningPost;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RunningPostResponse {
    private Long id;
    private Long hostUserId;
    private Long courseId;
    private String title;
    private RunningType runningType;
    private String locationName;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal targetDistance;
    private int averagePace;
    private LocalDateTime startAt;
    private int maxParticipants;
    private GenderPolicy genderPolicy;
    private RunningStatus status;

    public static RunningPostResponse from(RunningPost post) {
        return new RunningPostResponse(
                post.getId(),
                post.getHostUser().getId(),
                post.getCourse() != null ? post.getCourse().getId() : null,
                post.getTitle(),
                post.getRunningType(),
                post.getLocationName(),
                post.getLatitude(),
                post.getLongitude(),
                post.getTargetDistance(),
                post.getAveragePace(),
                post.getStartAt(),
                post.getMaxParticipants(),
                post.getGenderPolicy(),
                post.getStatus()
        );
    }
}
