package com.example.runspot.running.service;

import com.example.runspot.course.domain.entity.RunningCourse;
import com.example.runspot.course.domain.repository.RunningCourseRepository;
import com.example.runspot.running.api.dto.request.RunningPostCreateRequest;
import com.example.runspot.running.api.dto.response.RunningPostResponse;
import com.example.runspot.running.domain.RunningStatus;
import com.example.runspot.running.domain.entity.RunningPost;
import com.example.runspot.running.domain.repository.RunningPostRepository;
import com.example.runspot.user.domain.entity.User;
import com.example.runspot.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RunningPostService {

    private final RunningPostRepository runningPostRepository;
    private final UserRepository userRepository;
    private final RunningCourseRepository runningCourseRepository;

    @Transactional
    public RunningPostResponse createPost(Long userId, RunningPostCreateRequest request) {
        User hostUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        RunningCourse course = null;
        if (request.getCourseId() != null) {
            course = runningCourseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new IllegalArgumentException("코스를 찾을 수 없습니다."));
        }

        RunningPost post = RunningPost.builder()
                .hostUser(hostUser)
                .course(course)
                .title(request.getTitle())
                .runningType(request.getRunningType())
                .locationName(request.getLocationName())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .targetDistance(request.getTargetDistance())
                .averagePace(request.getAveragePace())
                .startAt(request.getStartAt())
                .maxParticipants(request.getMaxParticipants())
                .genderPolicy(request.getGenderPolicy())
                .build();

        RunningPost savedPost = runningPostRepository.save(post);
        return RunningPostResponse.from(savedPost);
    }

    public List<RunningPostResponse> getPosts() {
        return runningPostRepository.findAll().stream()
                .map(RunningPostResponse::from)
                .collect(Collectors.toList());
    }

    public RunningPostResponse getPost(Long postId) {
        RunningPost post = runningPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("러닝 모집글을 찾을 수 없습니다."));
        return RunningPostResponse.from(post);
    }

    public List<RunningPostResponse> getMyPosts(Long userId) {
        return runningPostRepository.findAllByHostUserIdOrderByCreatedAtDesc(userId).stream()
                .map(RunningPostResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updatePostStatus(Long userId, Long postId, RunningStatus newStatus) {
        RunningPost post = runningPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("러닝 모집글을 찾을 수 없습니다."));

        if (!post.getHostUser().getId().equals(userId)) {
            throw new IllegalArgumentException("모집글 상태를 변경할 권한이 없습니다.");
        }

        post.updateStatus(newStatus);
    }
}
