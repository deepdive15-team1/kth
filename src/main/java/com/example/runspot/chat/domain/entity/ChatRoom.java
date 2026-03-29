package com.example.runspot.chat.domain.entity;

import com.example.runspot.chat.domain.RoomType;
import com.example.runspot.running.domain.entity.RunningPost;
import com.example.runspot.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_rooms", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"running_post_id", "room_type"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    private RoomType roomType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "running_post_id")
    private RunningPost runningPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id", nullable = false)
    private User createdByUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direct_host_user_id")
    private User directHostUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direct_target_user_id")
    private User directTargetUser;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public ChatRoom(RoomType roomType, RunningPost runningPost, User createdByUser, User directHostUser, User directTargetUser) {
        this.roomType = roomType;
        this.runningPost = runningPost;
        this.createdByUser = createdByUser;
        this.directHostUser = directHostUser;
        this.directTargetUser = directTargetUser;
    }
}