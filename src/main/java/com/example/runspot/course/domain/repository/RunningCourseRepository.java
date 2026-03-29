package com.example.runspot.course.domain.repository;

import com.example.runspot.course.domain.entity.RunningCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RunningCourseRepository extends JpaRepository<RunningCourse, Long> {
    List<RunningCourse> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
