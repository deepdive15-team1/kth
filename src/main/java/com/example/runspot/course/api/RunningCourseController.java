package com.example.runspot.course.api;

import com.example.runspot.course.api.dto.request.CourseCreateRequest;
import com.example.runspot.course.api.dto.response.RunningCourseResponse;
import com.example.runspot.course.service.RunningCourseService;
import com.example.runspot.global.security.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/running-courses")
public class RunningCourseController {

    private final RunningCourseService courseService;

    @PostMapping
    public ResponseEntity<RunningCourseResponse> saveCourse(@Valid @RequestBody CourseCreateRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        RunningCourseResponse response = courseService.saveCourse(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<List<RunningCourseResponse>> getMyCourses() {
        Long userId = SecurityUtil.getCurrentUserId();
        List<RunningCourseResponse> responses = courseService.getMyCourses(userId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<RunningCourseResponse> getCourse(@PathVariable Long courseId) {
        RunningCourseResponse response = courseService.getCourse(courseId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        Long userId = SecurityUtil.getCurrentUserId();
        courseService.deleteCourse(userId, courseId);
        return ResponseEntity.noContent().build();
    }
}
