package com.kurierfree.server.domain.user.domain.enums;

public enum Status {
    PENDING, // 서포터즈 지원 완료
    REJECTED, // 서포터즈 선발 탈락
    MATCHING, // 선발 완료, 매칭대기중
    MATCHED // 매칭 완료
}
