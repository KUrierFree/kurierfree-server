package com.kurierfree.server.domain.timeTable.dto.response;

import com.kurierfree.server.domain.lesson.dto.response.LessonResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class TimeTableResponse {
    private List<LessonResponse> lessons;
}
