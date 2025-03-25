package com.kurierfree.server.domain.auth.dto;

public record JwtDto(
        String accessToken,
        String refreshToken) {

}
