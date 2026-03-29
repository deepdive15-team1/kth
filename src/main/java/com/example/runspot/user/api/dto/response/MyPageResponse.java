package com.example.runspot.user.api.dto.response;

import com.example.runspot.course.api.dto.response.RunningCourseResponse;
import com.example.runspot.running.api.dto.response.RunningApplicationResponse;
import com.example.runspot.running.api.dto.response.RunningPostResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MyPageResponse {
    private UserMeResponse userInfo;
    private List<RunningPostResponse> myHostedPosts;
    private List<RunningApplicationResponse> myApplications;
    private List<RunningCourseResponse> myCourses;
}
