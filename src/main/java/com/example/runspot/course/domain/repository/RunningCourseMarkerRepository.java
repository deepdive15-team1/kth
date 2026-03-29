package com.example.runspot.course.domain.repository;

import com.example.runspot.course.domain.entity.RunningCourseMarker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RunningCourseMarkerRepository extends JpaRepository<RunningCourseMarker, Long> {
    List<RunningCourseMarker> findAllByCourseId(Long courseId);
    void deleteAllByCourseId(Long courseId);
}
