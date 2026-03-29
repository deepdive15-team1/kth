package com.example.runspot.course.service;

import com.example.runspot.course.api.dto.request.CourseCreateRequest;
import com.example.runspot.course.api.dto.response.RunningCourseResponse;
import com.example.runspot.course.domain.entity.RunningCourse;
import com.example.runspot.course.domain.entity.RunningCourseMarker;
import com.example.runspot.course.domain.repository.RunningCourseMarkerRepository;
import com.example.runspot.course.domain.repository.RunningCourseRepository;
import com.example.runspot.user.domain.entity.User;
import com.example.runspot.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RunningCourseService {

    private final RunningCourseRepository courseRepository;
    private final RunningCourseMarkerRepository markerRepository;
    private final UserRepository userRepository;

    @Transactional
    public RunningCourseResponse saveCourse(Long userId, CourseCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        RunningCourse course = RunningCourse.builder()
                .user(user)
                .title(request.getTitle())
                .distance(request.getDistance())
                .polylineJson(request.getPolylineJson())
                .build();

        RunningCourse savedCourse = courseRepository.save(course);

        List<RunningCourseMarker> markers = Collections.emptyList();
        if (request.getMarkers() != null && !request.getMarkers().isEmpty()) {
            markers = request.getMarkers().stream()
                    .map(m -> RunningCourseMarker.builder()
                            .course(savedCourse)
                            .title(m.getTitle())
                            .description(m.getDescription())
                            .latitude(m.getLatitude())
                            .longitude(m.getLongitude())
                            .build())
                    .collect(Collectors.toList());
            markerRepository.saveAll(markers);
        }

        return RunningCourseResponse.of(savedCourse, markers);
    }

    public List<RunningCourseResponse> getMyCourses(Long userId) {
        List<RunningCourse> courses = courseRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
        
        return courses.stream().map(course -> {
            List<RunningCourseMarker> markers = markerRepository.findAllByCourseId(course.getId());
            return RunningCourseResponse.of(course, markers);
        }).collect(Collectors.toList());
    }

    public RunningCourseResponse getCourse(Long courseId) {
        RunningCourse course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("코스를 찾을 수 없습니다."));
        List<RunningCourseMarker> markers = markerRepository.findAllByCourseId(courseId);
        
        return RunningCourseResponse.of(course, markers);
    }

    @Transactional
    public void deleteCourse(Long userId, Long courseId) {
        RunningCourse course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("코스를 찾을 수 없습니다."));

        if (!course.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("자신의 코스만 삭제할 수 있습니다.");
        }

        markerRepository.deleteAllByCourseId(courseId);
        courseRepository.delete(course);
    }
}
