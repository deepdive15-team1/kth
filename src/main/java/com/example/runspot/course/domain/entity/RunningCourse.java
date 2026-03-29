package com.example.runspot.course.domain.entity;

import com.example.runspot.common.entity.BaseTimeEntity;
import com.example.runspot.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "running_courses")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RunningCourse extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 80)
    private String title;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal distance;

    @Column(name = "polyline_json", nullable = false, columnDefinition = "JSON")
    private String polylineJson;

    @Builder
    public RunningCourse(User user, String title, BigDecimal distance, String polylineJson) {
        this.user = user;
        this.title = title;
        this.distance = distance;
        this.polylineJson = polylineJson;
    }
}