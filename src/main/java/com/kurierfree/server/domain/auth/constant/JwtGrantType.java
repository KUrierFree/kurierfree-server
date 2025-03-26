package com.kurierfree.server.domain.auth.constant;

import lombok.Getter;

@Getter
public enum JwtGrantType {
    GRANT_TYPE_USER("USER"),
    GRANT_TYPE_ADMIN("ADMIN");

    private final String value;

    JwtGrantType(String value) {
        this.value = value;
    }

}
