package com.kurierfree.server.domain.auth.domain;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
public class JwtToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
