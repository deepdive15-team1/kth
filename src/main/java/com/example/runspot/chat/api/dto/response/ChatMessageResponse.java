package com.example.runspot.chat.api.dto.response;

import com.example.runspot.chat.domain.MessageType;
import com.example.runspot.chat.domain.entity.ChatMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatMessageResponse {
    private Long id;
    private Long chatRoomId;
    private Long senderUserId;
    private MessageType messageType;
    private String content;
    private LocalDateTime sentAt;

    public static ChatMessageResponse from(ChatMessage message) {
        return new ChatMessageResponse(
                message.getId(),
                message.getChatRoom().getId(),
                message.getSenderUser().getId(),
                message.getMessageType(),
                message.getContent(),
                message.getSentAt()
        );
    }
}
