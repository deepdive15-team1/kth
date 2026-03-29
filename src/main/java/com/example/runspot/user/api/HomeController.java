package com.example.runspot.user.api;

import com.example.runspot.global.security.SecurityUtil;
import com.example.runspot.user.api.dto.response.HomeResponse;
import com.example.runspot.user.api.dto.response.MyPageResponse;
import com.example.runspot.user.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/home")
    public ResponseEntity<HomeResponse> getHome() {
        HomeResponse response = homeService.getHome();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-page")
    public ResponseEntity<MyPageResponse> getMyPage() {
        Long userId = SecurityUtil.getCurrentUserId();
        MyPageResponse response = homeService.getMyPage(userId);
        return ResponseEntity.ok(response);
    }
}
