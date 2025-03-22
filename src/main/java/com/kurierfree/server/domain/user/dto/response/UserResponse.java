package com.kurierfree.server.domain.user.dto.response;

import com.kurierfree.server.domain.user.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long userId;
    private Role role;
}
