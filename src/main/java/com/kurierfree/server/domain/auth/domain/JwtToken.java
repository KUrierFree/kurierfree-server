package com.kurierfree.server.domain.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class JwtToken {
    private String accessToken;
    private String refreshToken;
    private String grantType;
}
