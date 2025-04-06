package com.kurierfree.server.domain.matching.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MatchingRequest {
    private Long SupporterId;
    private Long disabledStudentId;
}
