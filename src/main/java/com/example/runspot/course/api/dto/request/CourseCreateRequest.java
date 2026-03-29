package com.example.runspot.course.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseCreateRequest {

    @NotBlank(message = "코스 제목을 입력해주세요.")
    private String title;

    @NotNull(message = "총 거리를 입력해주세요.")
    private BigDecimal distance;

    @NotBlank(message = "폴리라인 데이터를 입력해주세요.")
    private String polylineJson;

    private List<MarkerRequest> markers;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MarkerRequest {
        @NotBlank(message = "마커 이름을 입력해주세요.")
        private String title;

        private String description;

        @NotNull(message = "위도를 입력해주세요.")
        private BigDecimal latitude;

        @NotNull(message = "경도를 입력해주세요.")
        private BigDecimal longitude;
    }
}
