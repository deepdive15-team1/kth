package com.example.runspot.running.domain.repository;

import com.example.runspot.running.domain.entity.RunningPost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RunningPostRepository extends JpaRepository<RunningPost, Long> {
    List<RunningPost> findAllByHostUserIdOrderByCreatedAtDesc(Long hostUserId);
}
