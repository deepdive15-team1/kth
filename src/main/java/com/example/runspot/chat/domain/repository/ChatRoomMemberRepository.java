package com.example.runspot.chat.domain.repository;

import com.example.runspot.chat.domain.entity.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
    List<ChatRoomMember> findAllByUserId(Long userId);
    List<ChatRoomMember> findAllByChatRoomId(Long chatRoomId);
    Optional<ChatRoomMember> findByChatRoomIdAndUserId(Long chatRoomId, Long userId);
    boolean existsByChatRoomIdAndUserId(Long chatRoomId, Long userId);
}
