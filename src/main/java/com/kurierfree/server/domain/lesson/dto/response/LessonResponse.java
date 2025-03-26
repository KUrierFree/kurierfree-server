package com.kurierfree.server.domain.lesson.dto.response;

import com.kurierfree.server.domain.lesson.domain.ClassDay;
import com.kurierfree.server.domain.lesson.domain.ClassTime;
import com.kurierfree.server.domain.lesson.domain.Lesson;
import com.kurierfree.server.domain.timeTable.domain.TimeTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LessonResponse {
    private Long id;
    private TimeTable timeTable;
    private String subject;
    private String professor;
    private ClassDay classDay;
    private ClassTime startTime;
    private ClassTime endTime;

    public static LessonResponse from(Lesson lesson) {
        return LessonResponse.builder()
                .id(lesson.getId())
                .timeTable(lesson.getTimeTable())
                .subject(lesson.getSubject())
                .professor(lesson.getProfessor())
                .classDay(lesson.getClassDay())
                .startTime(lesson.getStartTime())
                .endTime(lesson.getEndTime())
                .build();
    }
}
