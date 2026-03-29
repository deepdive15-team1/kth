package com.example.runspot.running.domain.repository;

import com.example.runspot.running.domain.ApplicationStatus;
import com.example.runspot.running.domain.entity.RunningApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RunningApplicationRepository extends JpaRepository<RunningApplication, Long> {
    List<RunningApplication> findAllByApplicantUserIdOrderByAppliedAtDesc(Long applicantUserId);
    List<RunningApplication> findAllByRunningPostId(Long runningPostId);
    List<RunningApplication> findAllByRunningPostIdAndStatus(Long runningPostId, ApplicationStatus status);
    boolean existsByRunningPostIdAndApplicantUserId(Long runningPostId, Long applicantUserId);
    Optional<RunningApplication> findByRunningPostIdAndApplicantUserId(Long runningPostId, Long applicantUserId);
}
