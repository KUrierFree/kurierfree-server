package com.kurierfree.server.domain.lesson.dto.response;

import com.kurierfree.server.domain.lesson.domain.ClassDay;
import com.kurierfree.server.domain.lesson.domain.LessonSchedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LessonScheduleResponse {
    private String classroom;
    private ClassDay classDay;
    private String startTime;
    private String endTime;

    public static LessonScheduleResponse from(LessonSchedule lessonSchedule) {
        return LessonScheduleResponse.builder()
                .classDay(lessonSchedule.getClassDay())
                .startTime(lessonSchedule.getStartTime().toString())
                .endTime(lessonSchedule.getEndTime().toString())
                .classroom(lessonSchedule.getClassroom())
                .build();
    }
}
