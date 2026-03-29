package com.example.runspot.running.api.dto.request;

import com.example.runspot.running.domain.GenderPolicy;
import com.example.runspot.running.domain.RunningType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RunningPostCreateRequest {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotNull(message = "러닝 종류를 선택해주세요.")
    private RunningType runningType;

    @NotBlank(message = "모임 장소를 입력해주세요.")
    private String locationName;

    @NotNull(message = "위도를 입력해주세요.")
    private BigDecimal latitude;

    @NotNull(message = "경도를 입력해주세요.")
    private BigDecimal longitude;

    @NotNull(message = "목표 거리를 입력해주세요.")
    private BigDecimal targetDistance;

    @NotNull(message = "평균 페이스를 입력해주세요.")
    private Integer averagePace;

    @NotNull(message = "모임 시작 일시를 입력해주세요.")
    private LocalDateTime startAt;

    @NotNull(message = "모집 인원을 입력해주세요.")
    private Integer maxParticipants;

    @NotNull(message = "참여 성별 정책을 선택해주세요.")
    private GenderPolicy genderPolicy;

    private Long courseId;
}
