package com.example.runspot.chat.service;

import com.example.runspot.chat.api.dto.request.ChatMessageCreateRequest;
import com.example.runspot.chat.api.dto.response.ChatMessageResponse;
import com.example.runspot.chat.domain.entity.ChatMessage;
import com.example.runspot.chat.domain.entity.ChatRoom;
import com.example.runspot.chat.domain.repository.ChatMessageRepository;
import com.example.runspot.chat.domain.repository.ChatRoomRepository;
import com.example.runspot.user.domain.entity.User;
import com.example.runspot.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    @Transactional
    public ChatMessageResponse saveMessage(Long chatRoomId, ChatMessageCreateRequest request) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방을 찾을 수 없습니다."));

        User sender = userRepository.findById(request.getSenderUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        ChatMessage message = ChatMessage.builder()
                .chatRoom(chatRoom)
                .senderUser(sender)
                .messageType(request.getMessageType())
                .content(request.getContent())
                .build();

        ChatMessage savedMessage = chatMessageRepository.save(message);
        return ChatMessageResponse.from(savedMessage);
    }
}
