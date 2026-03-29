package com.example.runspot.chat.api.dto.request;

import com.example.runspot.chat.domain.MessageType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageCreateRequest {
    private Long senderUserId;
    private MessageType messageType;
    private String content;
}
