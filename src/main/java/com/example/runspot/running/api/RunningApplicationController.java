package com.example.runspot.running.api;

import com.example.runspot.chat.api.dto.response.ChatRoomResponse;
import com.example.runspot.chat.service.ChatRoomService;
import com.example.runspot.global.security.SecurityUtil;
import com.example.runspot.running.api.dto.request.ApplicationStatusUpdateRequest;
import com.example.runspot.running.api.dto.response.RunningApplicationResponse;
import com.example.runspot.running.service.RunningApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/running-applications")
public class RunningApplicationController {

    private final RunningApplicationService runningApplicationService;
    private final ChatRoomService chatRoomService;

    @GetMapping("/me")
    public ResponseEntity<List<RunningApplicationResponse>> getMyApplications() {
        Long userId = SecurityUtil.getCurrentUserId();
        List<RunningApplicationResponse> responses = runningApplicationService.getMyApplications(userId);
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{applicationId}/status")
    public ResponseEntity<Void> updateApplicationStatus(
            @PathVariable Long applicationId,
            @Valid @RequestBody ApplicationStatusUpdateRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        runningApplicationService.updateApplicationStatus(userId, applicationId, request.getStatus());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{applicationId}/direct-chat-room")
    public ResponseEntity<ChatRoomResponse> createDirectChatRoom(@PathVariable Long applicationId) {
        Long userId = SecurityUtil.getCurrentUserId();
        ChatRoomResponse response = chatRoomService.createDirectChatRoom(userId, applicationId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
