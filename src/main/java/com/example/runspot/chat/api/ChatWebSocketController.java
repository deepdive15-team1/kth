package com.example.runspot.chat.api;

import com.example.runspot.chat.api.dto.request.ChatMessageCreateRequest;
import com.example.runspot.chat.api.dto.response.ChatMessageResponse;
import com.example.runspot.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/rooms/{chatRoomId}/message")
    public void sendMessage(@DestinationVariable Long chatRoomId, ChatMessageCreateRequest request) {
        ChatMessageResponse response = chatMessageService.saveMessage(chatRoomId, request);
        messagingTemplate.convertAndSend("/sub/chat/rooms/" + chatRoomId, response);
    }
}
