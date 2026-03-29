package com.example.runspot.chat.service;

import com.example.runspot.chat.api.dto.response.ChatMessageResponse;
import com.example.runspot.chat.api.dto.response.ChatRoomResponse;
import com.example.runspot.chat.domain.RoomType;
import com.example.runspot.chat.domain.entity.ChatRoom;
import com.example.runspot.chat.domain.entity.ChatRoomMember;
import com.example.runspot.chat.domain.repository.ChatMessageRepository;
import com.example.runspot.chat.domain.repository.ChatRoomMemberRepository;
import com.example.runspot.chat.domain.repository.ChatRoomRepository;
import com.example.runspot.running.domain.entity.RunningApplication;
import com.example.runspot.running.domain.repository.RunningApplicationRepository;
import com.example.runspot.user.domain.entity.User;
import com.example.runspot.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final RunningApplicationRepository applicationRepository;

    @Transactional
    public ChatRoomResponse createDirectChatRoom(Long userId, Long applicationId) {
        User creator = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        RunningApplication application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("신청 내역을 찾을 수 없습니다."));

        User host = application.getRunningPost().getHostUser();
        User applicant = application.getApplicantUser();

        // 요청자가 호스트도, 신청자도 아니면 생성 불가
        if (!creator.getId().equals(host.getId()) && !creator.getId().equals(applicant.getId())) {
            throw new IllegalArgumentException("채팅방을 생성할 권한이 없습니다.");
        }

        User target = creator.getId().equals(host.getId()) ? applicant : host;

        // 이미 채팅방이 있는지 확인 (두 사용자 ID 조합으로)
        // 이 부분은 로직이 복잡해서, 간단하게는 생성자로만 체크하거나 별도 테이블로 관리할 수 있음, 없애도됨
        // 우선 간단히 생성만

        ChatRoom chatRoom = ChatRoom.builder()
                .roomType(RoomType.DIRECT)
                .createdByUser(creator)
                .directHostUser(host)
                .directTargetUser(applicant)
                .build();

        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);

        // 채팅방 멤버 추가
        ChatRoomMember member1 = ChatRoomMember.builder().chatRoom(savedChatRoom).user(host).build();
        ChatRoomMember member2 = ChatRoomMember.builder().chatRoom(savedChatRoom).user(applicant).build();
        chatRoomMemberRepository.saveAll(List.of(member1, member2));

        return ChatRoomResponse.from(savedChatRoom);
    }

    public List<ChatRoomResponse> getMyChatRooms(Long userId) {
        List<ChatRoomMember> memberships = chatRoomMemberRepository.findAllByUserId(userId);
        return memberships.stream()
                .map(member -> ChatRoomResponse.from(member.getChatRoom()))
                .collect(Collectors.toList());
    }

    public ChatRoomResponse getChatRoom(Long userId, Long chatRoomId) {
        ChatRoomMember member = chatRoomMemberRepository.findByChatRoomIdAndUserId(chatRoomId, userId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방에 참여하고 있지 않습니다."));
        return ChatRoomResponse.from(member.getChatRoom());
    }

    public List<ChatMessageResponse> getChatMessages(Long userId, Long chatRoomId) {
        if (!chatRoomMemberRepository.existsByChatRoomIdAndUserId(chatRoomId, userId)) {
            throw new IllegalArgumentException("채팅방에 참여하고 있지 않습니다.");
        }
        return chatMessageRepository.findAllByChatRoomIdOrderBySentAtAsc(chatRoomId).stream()
                .map(ChatMessageResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateLastReadMessage(Long userId, Long chatRoomId, Long lastMessageId) {
        ChatRoomMember member = chatRoomMemberRepository.findByChatRoomIdAndUserId(chatRoomId, userId)
                .orElseThrow(() -> new IllegalArgumentException("채팅방에 참여하고 있지 않습니다."));
        member.updateLastReadMessageId(lastMessageId);
    }
}
