package com.example.runspot.chat.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DirectChatCreateRequest {

    @NotNull(message = "상대방 유저 ID를 입력해주세요.")
    private Long targetUserId;
}
