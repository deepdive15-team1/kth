package com.example.runspot.chat.domain.repository;

import com.example.runspot.chat.domain.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByChatRoomIdOrderBySentAtAsc(Long chatRoomId);
}
