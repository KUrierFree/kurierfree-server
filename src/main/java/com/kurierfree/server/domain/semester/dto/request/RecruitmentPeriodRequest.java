package com.kurierfree.server.domain.semester.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class RecruitmentPeriodRequest {
    private LocalDate startDate;
    private LocalDate endDate;
}
