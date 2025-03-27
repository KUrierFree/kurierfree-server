package com.kurierfree.server.domain.lesson.dto.response;

import com.kurierfree.server.domain.lesson.domain.ClassDay;
import com.kurierfree.server.domain.lesson.domain.Lesson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LessonResponse {
    private Long id;
    private String subject;
    private String professor;
    private ClassDay classDay;
    private String startTime;
    private String endTime;

    public static LessonResponse from(Lesson lesson) {
        return LessonResponse.builder()
                .id(lesson.getId())
                .subject(lesson.getSubject())
                .professor(lesson.getProfessor())
                .classDay(lesson.getClassDay())
                .startTime(lesson.getStartTime().toString())
                .endTime(lesson.getEndTime().toString())
                .build();
    }
}
