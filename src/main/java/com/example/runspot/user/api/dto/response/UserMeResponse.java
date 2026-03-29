package com.example.runspot.user.api.dto.response;

import com.example.runspot.user.domain.AgeGroup;
import com.example.runspot.user.domain.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserMeResponse {
    private Long id;
    private String loginId;
    private String name;
    private AgeGroup ageGroup;
    private int weeklyRunningCount;
    private int averagePace;

    public static UserMeResponse from(User user) {
        return new UserMeResponse(
                user.getId(),
                user.getLoginId(),
                user.getName(),
                user.getAgeGroup(),
                user.getWeeklyRunningCount(),
                user.getAveragePace()
        );
    }
}
