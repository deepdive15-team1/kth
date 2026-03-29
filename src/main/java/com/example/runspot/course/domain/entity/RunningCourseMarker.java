package com.example.runspot.course.domain.entity;

import com.example.runspot.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "running_course_markers")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RunningCourseMarker extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private RunningCourse course;

    @Column(nullable = false, length = 60)
    private String title;

    @Column(length = 200)
    private String description;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal longitude;

    @Builder
    public RunningCourseMarker(RunningCourse course, String title, String description, BigDecimal latitude, BigDecimal longitude) {
        this.course = course;
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}