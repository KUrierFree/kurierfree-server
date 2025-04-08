package com.kurierfree.server.domain.list.dto.response;

import com.kurierfree.server.domain.user.domain.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MatchedSupporterResponse {
    private Long supporterId;
    private String name;
    private String department;
    private Gender gender;
    private String grade;
}
