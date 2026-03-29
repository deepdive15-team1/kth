package com.example.runspot.user.api;

import com.example.runspot.global.security.SecurityUtil;
import com.example.runspot.user.api.dto.response.UserMeResponse;
import com.example.runspot.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserMeResponse> getMe() {
        Long userId = SecurityUtil.getCurrentUserId();
        UserMeResponse response = userService.getMe(userId);
        return ResponseEntity.ok(response);
    }
}
