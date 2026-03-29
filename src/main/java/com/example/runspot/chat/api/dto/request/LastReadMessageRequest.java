package com.example.runspot.chat.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LastReadMessageRequest {

    @NotNull(message = "마지막으로 읽은 메시지 ID를 입력해주세요.")
    private Long lastMessageId;
}
