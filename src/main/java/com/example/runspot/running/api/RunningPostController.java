package com.example.runspot.running.api;

import com.example.runspot.course.api.dto.response.RunningCourseResponse;
import com.example.runspot.course.service.RunningCourseService;
import com.example.runspot.global.security.SecurityUtil;
import com.example.runspot.running.api.dto.request.ApplicationCreateRequest;
import com.example.runspot.running.api.dto.request.RunningPostCreateRequest;
import com.example.runspot.running.api.dto.request.RunningStatusUpdateRequest;
import com.example.runspot.running.api.dto.response.RunningApplicationResponse;
import com.example.runspot.running.api.dto.response.RunningPostResponse;
import com.example.runspot.running.service.RunningApplicationService;
import com.example.runspot.running.service.RunningPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/running-posts")
public class RunningPostController {

    private final RunningPostService runningPostService;
    private final RunningApplicationService runningApplicationService;
    private final RunningCourseService runningCourseService;

    @PostMapping
    public ResponseEntity<RunningPostResponse> createPost(@Valid @RequestBody RunningPostCreateRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        RunningPostResponse response = runningPostService.createPost(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<RunningPostResponse>> getPosts() {
        List<RunningPostResponse> responses = runningPostService.getPosts();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{runningPostId}")
    public ResponseEntity<RunningPostResponse> getPost(@PathVariable Long runningPostId) {
        RunningPostResponse response = runningPostService.getPost(runningPostId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<List<RunningPostResponse>> getMyPosts() {
        Long userId = SecurityUtil.getCurrentUserId();
        List<RunningPostResponse> responses = runningPostService.getMyPosts(userId);
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{runningPostId}/status")
    public ResponseEntity<Void> updatePostStatus(
            @PathVariable Long runningPostId,
            @Valid @RequestBody RunningStatusUpdateRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        runningPostService.updatePostStatus(userId, runningPostId, request.getStatus());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{runningPostId}/applications")
    public ResponseEntity<RunningApplicationResponse> applyForRunning(
            @PathVariable Long runningPostId,
            @RequestBody ApplicationCreateRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        RunningApplicationResponse response = runningApplicationService.apply(userId, runningPostId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{runningPostId}/applications")
    public ResponseEntity<List<RunningApplicationResponse>> getApplications(@PathVariable Long runningPostId) {
        Long userId = SecurityUtil.getCurrentUserId();
        List<RunningApplicationResponse> responses = runningApplicationService.getApplicationsForPost(userId, runningPostId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{runningPostId}/participants")
    public ResponseEntity<List<RunningApplicationResponse>> getParticipants(@PathVariable Long runningPostId) {
        List<RunningApplicationResponse> responses = runningApplicationService.getParticipants(runningPostId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{runningPostId}/course-details")
    public ResponseEntity<RunningCourseResponse> getCourseDetails(@PathVariable Long runningPostId) {
        Long userId = SecurityUtil.getCurrentUserId();
        RunningPostResponse post = runningPostService.getPost(runningPostId);

        if (post.getCourseId() == null) {
            return ResponseEntity.notFound().build();
        }

        RunningCourseResponse courseResponse = runningCourseService.getCourse(post.getCourseId());
        return ResponseEntity.ok(courseResponse);
    }
}
