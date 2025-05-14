package com.kurierfree.server.domain.semester.dto.response;

import com.kurierfree.server.domain.semester.domain.Semester;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class RecruitmentPeriodResponse {
    private LocalDate applicationStart;
    private LocalDate applicationEnd;
    private boolean isApplicationBeforeNow;

    private LocalDate selectionStart;
    private LocalDate selectionEnd;
    private boolean isSelectionBeforeNow;

    public static RecruitmentPeriodResponse from(Semester semester, LocalDate now) {
        return RecruitmentPeriodResponse.builder()
                .applicationStart(semester.getApplicationStartDate())
                .applicationEnd(semester.getApplicationEndDate())
                .isApplicationBeforeNow(isBefore(now, semester.getApplicationEndDate()))

                .selectionStart(semester.getSelectionStartDate())
                .selectionEnd(semester.getSelectionEndDate())
                .isSelectionBeforeNow(isBefore(now, semester.getSelectionEndDate()))
                .build();
    }

    private static boolean isBefore(LocalDate now, LocalDate end) {
        return end != null && !now.isAfter(end);
    }
}
