package com.kurierfree.server.domain.matching.dto.response;

import com.kurierfree.server.domain.user.domain.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MatchingSupporters {
    private int rank;
    private Long supporterId;
    private String name;
    private String department;
    private Gender gender;
    private String grade;
}
