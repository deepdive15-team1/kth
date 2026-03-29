package com.example.runspot.running.domain.entity;

import com.example.runspot.running.domain.ApplicationStatus;
import com.example.runspot.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "running_applications", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"running_post_id", "applicant_user_id"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RunningApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "running_post_id", nullable = false)
    private RunningPost runningPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicant_user_id", nullable = false)
    private User applicantUser;

    @Column(name = "message_to_host", length = 300)
    private String messageToHost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status = ApplicationStatus.PENDING;

    @Column(name = "applied_at", nullable = false)
    private LocalDateTime appliedAt;

    @Column(name = "decided_at")
    private LocalDateTime decidedAt;

    @Builder
    public RunningApplication(RunningPost runningPost, User applicantUser, String messageToHost) {
        this.runningPost = runningPost;
        this.applicantUser = applicantUser;
        this.messageToHost = messageToHost;
        this.status = ApplicationStatus.PENDING;
        this.appliedAt = LocalDateTime.now();
    }

    public void updateStatus(ApplicationStatus status) {
        this.status = status;
        this.decidedAt = LocalDateTime.now();
    }
}