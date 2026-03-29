package com.example.runspot.chat.api;

import com.example.runspot.chat.api.dto.request.LastReadMessageRequest;
import com.example.runspot.chat.api.dto.response.ChatMessageResponse;
import com.example.runspot.chat.api.dto.response.ChatRoomResponse;
import com.example.runspot.chat.service.ChatRoomService;
import com.example.runspot.global.security.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat-rooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping
    public ResponseEntity<List<ChatRoomResponse>> getMyChatRooms() {
        Long userId = SecurityUtil.getCurrentUserId();
        List<ChatRoomResponse> responses = chatRoomService.getMyChatRooms(userId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{chatRoomId}")
    public ResponseEntity<ChatRoomResponse> getChatRoom(@PathVariable Long chatRoomId) {
        Long userId = SecurityUtil.getCurrentUserId();
        ChatRoomResponse response = chatRoomService.getChatRoom(userId, chatRoomId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{chatRoomId}/messages")
    public ResponseEntity<List<ChatMessageResponse>> getChatMessages(@PathVariable Long chatRoomId) {
        Long userId = SecurityUtil.getCurrentUserId();
        List<ChatMessageResponse> responses = chatRoomService.getChatMessages(userId, chatRoomId);
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{chatRoomId}/read")
    public ResponseEntity<Void> readMessages(
            @PathVariable Long chatRoomId,
            @Valid @RequestBody LastReadMessageRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        chatRoomService.updateLastReadMessage(userId, chatRoomId, request.getLastMessageId());
        return ResponseEntity.ok().build();
    }
}
