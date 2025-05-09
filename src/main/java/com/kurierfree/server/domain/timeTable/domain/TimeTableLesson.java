package com.kurierfree.server.domain.timeTable.domain;

import com.kurierfree.server.domain.lesson.domain.Lesson;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "time_table_lesson")
public class TimeTableLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "timetable_id", nullable = false)
    private TimeTable timeTable;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Builder
    private TimeTableLesson(TimeTable timeTable, Lesson lesson) {
        this.timeTable = timeTable;
        this.lesson = lesson;
    }

    public static TimeTableLesson of(TimeTable timeTable, Lesson lesson) {
        return TimeTableLesson.builder()
                .timeTable(timeTable)
                .lesson(lesson)
                .build();
    }

}
