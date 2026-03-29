package com.example.runspot.user.service;

import com.example.runspot.course.api.dto.response.RunningCourseResponse;
import com.example.runspot.course.service.RunningCourseService;
import com.example.runspot.running.api.dto.response.RunningApplicationResponse;
import com.example.runspot.running.api.dto.response.RunningPostResponse;
import com.example.runspot.running.service.RunningApplicationService;
import com.example.runspot.running.service.RunningPostService;
import com.example.runspot.user.api.dto.response.HomeResponse;
import com.example.runspot.user.api.dto.response.MyPageResponse;
import com.example.runspot.user.api.dto.response.UserMeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeService {

    private final UserService userService;
    private final RunningPostService runningPostService;
    private final RunningApplicationService runningApplicationService;
    private final RunningCourseService runningCourseService;

    public HomeResponse getHome() {
        // 일단 모든 포스트를 가져와서 최신순으로 정렬된 데이터를 보여줌, 가공해서 사용해야 될듯
        List<RunningPostResponse> recentPosts = runningPostService.getPosts();
        return new HomeResponse(recentPosts);
    }

    public MyPageResponse getMyPage(Long userId) {
        UserMeResponse userInfo = userService.getMe(userId);
        List<RunningPostResponse> myHostedPosts = runningPostService.getMyPosts(userId);
        List<RunningApplicationResponse> myApplications = runningApplicationService.getMyApplications(userId);
        List<RunningCourseResponse> myCourses = runningCourseService.getMyCourses(userId);

        return new MyPageResponse(userInfo, myHostedPosts, myApplications, myCourses);
    }
}
