package com.example.runspot.running.service;

import com.example.runspot.running.api.dto.request.ApplicationCreateRequest;
import com.example.runspot.running.api.dto.response.RunningApplicationResponse;
import com.example.runspot.running.domain.ApplicationStatus;
import com.example.runspot.running.domain.RunningStatus;
import com.example.runspot.running.domain.entity.RunningApplication;
import com.example.runspot.running.domain.entity.RunningPost;
import com.example.runspot.running.domain.repository.RunningApplicationRepository;
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
public class RunningApplicationService {

    private final RunningApplicationRepository applicationRepository;
    private final RunningPostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public RunningApplicationResponse apply(Long userId, Long postId, ApplicationCreateRequest request) {
        User applicant = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        RunningPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("러닝 모집글을 찾을 수 없습니다."));

        if (post.getStatus() != RunningStatus.OPEN) {
            throw new IllegalArgumentException("모집 중인 러닝이 아닙니다.");
        }

        if (post.getHostUser().getId().equals(userId)) {
            throw new IllegalArgumentException("호스트는 자신의 모임에 신청할 수 없습니다.");
        }

        if (applicationRepository.existsByRunningPostIdAndApplicantUserId(postId, userId)) {
            throw new IllegalArgumentException("이미 신청한 러닝입니다.");
        }

        RunningApplication application = RunningApplication.builder()
                .runningPost(post)
                .applicantUser(applicant)
                .messageToHost(request.getMessageToHost())
                .build();

        RunningApplication savedApplication = applicationRepository.save(application);
        return RunningApplicationResponse.from(savedApplication);
    }

    public List<RunningApplicationResponse> getMyApplications(Long userId) {
        return applicationRepository.findAllByApplicantUserIdOrderByAppliedAtDesc(userId).stream()
                .map(RunningApplicationResponse::from)
                .collect(Collectors.toList());
    }

    public List<RunningApplicationResponse> getApplicationsForPost(Long userId, Long postId) {
        RunningPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("러닝 모집글을 찾을 수 없습니다."));

        if (!post.getHostUser().getId().equals(userId)) {
            throw new IllegalArgumentException("신청자 목록은 호스트만 조회할 수 있습니다.");
        }

        return applicationRepository.findAllByRunningPostId(postId).stream()
                .map(RunningApplicationResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateApplicationStatus(Long userId, Long applicationId, ApplicationStatus newStatus) {
        RunningApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("신청 내역을 찾을 수 없습니다."));

        RunningPost post = application.getRunningPost();

        // 취소는 신청자 본인만 가능
        if (newStatus == ApplicationStatus.CANCELED) {
            if (!application.getApplicantUser().getId().equals(userId)) {
                throw new IllegalArgumentException("자신의 신청만 취소할 수 있습니다.");
            }
        } else {
            // 승인/거절은 호스트만 가능
            if (!post.getHostUser().getId().equals(userId)) {
                throw new IllegalArgumentException("신청 상태는 호스트만 변경할 수 있습니다.");
            }
            
            if (newStatus == ApplicationStatus.APPROVED) {
                // 승인 시 모집 인원 체크 등 추가 로직 필요 함
                long approvedCount = applicationRepository.findAllByRunningPostIdAndStatus(post.getId(), ApplicationStatus.APPROVED).size();
                if (approvedCount >= post.getMaxParticipants()) {
                    throw new IllegalArgumentException("모집 인원이 마감되었습니다.");
                }
            }
        }

        application.updateStatus(newStatus);
    }

    public List<RunningApplicationResponse> getParticipants(Long postId) {
        return applicationRepository.findAllByRunningPostIdAndStatus(postId, ApplicationStatus.APPROVED).stream()
                .map(RunningApplicationResponse::from)
                .collect(Collectors.toList());
    }
}
