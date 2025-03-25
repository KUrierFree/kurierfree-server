package com.kurierfree.server.domain.user.dto.response;

import com.kurierfree.server.domain.auth.domain.JwtToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserLoginResponse {
    private String message;
    private UserResponse user;
    private JwtToken token;
}
