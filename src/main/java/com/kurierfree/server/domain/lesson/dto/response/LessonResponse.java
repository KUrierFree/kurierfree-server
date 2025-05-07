package com.kurierfree.server.domain.lesson.dto.response;

import com.kurierfree.server.domain.lesson.domain.Lesson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class LessonResponse {
    private Long id;
    private String subject;
    private String professor;
    private List<LessonScheduleResponse> lessonSchedule;

    public static LessonResponse from(Lesson lesson, List<LessonScheduleResponse> lessonScheduleResponse) {
        return LessonResponse.builder()
                .id(lesson.getId())
                .subject(lesson.getSubject())
                .professor(lesson.getProfessor())
                .lessonSchedule(lessonScheduleResponse)
                .build();
    }
}
