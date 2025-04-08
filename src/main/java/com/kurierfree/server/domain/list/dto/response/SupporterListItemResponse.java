package com.kurierfree.server.domain.list.dto.response;

import com.kurierfree.server.domain.user.domain.enums.Gender;
import com.kurierfree.server.domain.user.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SupporterListItemResponse {
    private Long supporterId;
    private String name;
    private String department;
    private Gender gender;
    private String grade;
    private Status status;
}
