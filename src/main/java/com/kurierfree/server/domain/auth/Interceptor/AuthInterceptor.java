package com.kurierfree.server.domain.auth.Interceptor;

import com.kurierfree.server.domain.auth.infra.JwtProvider;
import com.kurierfree.server.domain.user.dao.UserRepository;
import com.kurierfree.server.domain.user.domain.User;
import com.kurierfree.server.domain.user.domain.enums.Role;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public AuthInterceptor(JwtProvider jwtProvider, UserRepository userRepository) {
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();

        // /api/admin으로 시작하는 요청만 토큰 검사를 진행
        if (path.startsWith("/api/admin")) {
            return !validateAdminLogin(request, response); // 요청을 중단
        }

        return true; // 요청을 계속 진행
    }

    private boolean validateAdminLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader("Authorization");

        // 토큰이 없거나 형식이 잘못된 경우
        if (token == null || !token.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 필요합니다.");
            return true;
        }

        String jwtToken = token.substring(7); // "Bearer "를 제외한 토큰
        Long userId = jwtProvider.getUserIdFromToken(jwtToken);

        // 사용자 존재 여부 및 역할 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    try {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "사용자를 찾을 수 없습니다.");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return new EntityNotFoundException();
                });

        if (user.getRole() != Role.ADMIN) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "관리자만 접근할 수 있습니다.");
            return true;
        }
        return false;
    }
}
