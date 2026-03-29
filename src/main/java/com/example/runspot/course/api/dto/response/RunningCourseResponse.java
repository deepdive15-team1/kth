package com.example.runspot.course.api.dto.response;

import com.example.runspot.course.domain.entity.RunningCourse;
import com.example.runspot.course.domain.entity.RunningCourseMarker;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RunningCourseResponse {
    private Long id;
    private Long userId;
    private String title;
    private BigDecimal distance;
    private String polylineJson;
    private List<MarkerResponse> markers;

    public static RunningCourseResponse of(RunningCourse course, List<RunningCourseMarker> markers) {
        List<MarkerResponse> markerResponses = markers.stream()
                .map(MarkerResponse::from)
                .collect(Collectors.toList());

        return new RunningCourseResponse(
                course.getId(),
                course.getUser().getId(),
                course.getTitle(),
                course.getDistance(),
                course.getPolylineJson(),
                markerResponses
        );
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class MarkerResponse {
        private Long id;
        private String title;
        private String description;
        private BigDecimal latitude;
        private BigDecimal longitude;

        public static MarkerResponse from(RunningCourseMarker marker) {
            return new MarkerResponse(
                    marker.getId(),
                    marker.getTitle(),
                    marker.getDescription(),
                    marker.getLatitude(),
                    marker.getLongitude()
            );
        }
    }
}
