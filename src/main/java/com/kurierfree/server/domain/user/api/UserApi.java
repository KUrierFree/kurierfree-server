package com.kurierfree.server.domain.user.api;

import com.kurierfree.server.domain.user.application.UserService;
import com.kurierfree.server.domain.user.dto.request.UserRegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class UserApi {
    private final UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "회원가입")
    public ResponseEntity<Long> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        try {
            Long userId = userService.register(userRegisterRequest);
            return ResponseEntity.ok(userId);
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
