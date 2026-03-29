package com.example.runspot.user.domain.entity;

import com.example.runspot.common.entity.BaseTimeEntity;
import com.example.runspot.user.domain.AgeGroup;
import com.example.runspot.user.domain.UserRole;
import com.example.runspot.user.domain.UserStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_id", nullable = false, unique = true, length = 30)
    private String loginId;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "age_group", nullable = false, length = 20)
    private AgeGroup ageGroup;

    @Column(name = "weekly_running_count", nullable = false)
    private int weeklyRunningCount;

    @Column(name = "average_pace", nullable = false)
    private int averagePace;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserStatus status;

    @Builder
    public User(String loginId, String password, String name, AgeGroup ageGroup, Integer weeklyRunningCount, Integer averagePace, UserRole role, UserStatus status){
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.ageGroup = ageGroup;
        this.weeklyRunningCount = weeklyRunningCount;
        this.averagePace = averagePace;
        this.role = role;
        this.status = status;
    }

    public static User create(String loginId, String password, String name,
                              AgeGroup ageGroup, Integer weeklyRunningCount, Integer averagePace) {

        return User.builder()
                .loginId(loginId)
                .password(password)
                .name(name)
                .ageGroup(ageGroup)
                .weeklyRunningCount(weeklyRunningCount)
                .averagePace(averagePace)
                .role(UserRole.USER)
                .status(UserStatus.ACTIVE)
                .build();
    }
}
