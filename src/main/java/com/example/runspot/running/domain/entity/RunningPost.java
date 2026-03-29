package com.example.runspot.running.domain.entity;

import com.example.runspot.common.entity.BaseTimeEntity;
import com.example.runspot.course.domain.entity.RunningCourse;
import com.example.runspot.running.domain.GenderPolicy;
import com.example.runspot.running.domain.RunningStatus;
import com.example.runspot.running.domain.RunningType;
import com.example.runspot.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "running_posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RunningPost extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_user_id", nullable = false)
    private User hostUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private RunningCourse course;

    @Column(nullable = false, length = 60)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "running_type", nullable = false)
    private RunningType runningType;

    @Column(name = "location_name", nullable = false, length = 80)
    private String locationName;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "target_distance", nullable = false, precision = 5, scale = 2)
    private BigDecimal targetDistance;

    @Column(name = "average_pace", nullable = false)
    private int averagePace;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "max_participants", nullable = false)
    private int maxParticipants;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender_policy", nullable = false)
    private GenderPolicy genderPolicy;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RunningStatus status = RunningStatus.OPEN;

    @Builder
    public RunningPost(User hostUser, RunningCourse course, String title, RunningType runningType,
                       String locationName, BigDecimal latitude, BigDecimal longitude,
                       BigDecimal targetDistance, int averagePace, LocalDateTime startAt,
                       int maxParticipants, GenderPolicy genderPolicy) {
        this.hostUser = hostUser;
        this.course = course;
        this.title = title;
        this.runningType = runningType;
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.targetDistance = targetDistance;
        this.averagePace = averagePace;
        this.startAt = startAt;
        this.maxParticipants = maxParticipants;
        this.genderPolicy = genderPolicy;
        this.status = RunningStatus.OPEN;
    }

    public void updateStatus(RunningStatus status) {
        this.status = status;
    }
}