package com.kurierfree.server.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserLoginResponse {
    private String message;
    private UserResponse user;
    private String token;
}
