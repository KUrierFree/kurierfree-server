package com.kurierfree.server.domain.list.dto.request;

import com.kurierfree.server.domain.user.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SupporterStatusUpdateRequest {
    private Long supporterId;
    private Status status;
}
