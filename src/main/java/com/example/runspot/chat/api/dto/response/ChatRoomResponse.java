package com.example.runspot.chat.api.dto.response;

import com.example.runspot.chat.domain.RoomType;
import com.example.runspot.chat.domain.entity.ChatRoom;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatRoomResponse {
    private Long id;
    private RoomType roomType;
    private Long runningPostId;
    private Long createdByUserId;
    private Long directHostUserId;
    private Long directTargetUserId;
    private LocalDateTime createdAt;

    public static ChatRoomResponse from(ChatRoom chatRoom) {
        return new ChatRoomResponse(
                chatRoom.getId(),
                chatRoom.getRoomType(),
                chatRoom.getRunningPost() != null ? chatRoom.getRunningPost().getId() : null,
                chatRoom.getCreatedByUser().getId(),
                chatRoom.getDirectHostUser() != null ? chatRoom.getDirectHostUser().getId() : null,
                chatRoom.getDirectTargetUser() != null ? chatRoom.getDirectTargetUser().getId() : null,
                chatRoom.getCreatedAt()
        );
    }
}
