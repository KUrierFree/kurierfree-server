package com.kurierfree.server.domain.user.api;

import com.kurierfree.server.domain.user.application.UserService;
import com.kurierfree.server.domain.user.dto.request.UserLoginRequest;
import com.kurierfree.server.domain.user.dto.request.UserRegisterRequest;
import com.kurierfree.server.domain.user.dto.response.UserLoginResponse;
import com.kurierfree.server.domain.user.dto.response.UserRegisterResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserApi {
    private final UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "회원가입")
    public ResponseEntity<UserRegisterResponse> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        try {
            UserRegisterResponse userRegisterResponse = userService.register(userRegisterRequest);
            return ResponseEntity.ok(userRegisterResponse);
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        try {
            UserLoginResponse userLoginResponse = userService.login(userLoginRequest);
            return ResponseEntity.ok(userLoginResponse);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/logout")
    @Operation(summary = "로그아웃")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        try{
            Long userId = userService.logout(token);
            return ResponseEntity.ok("id: " + userId + "님이 로그아웃에 성공하였습니다.");
        }catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }

}
