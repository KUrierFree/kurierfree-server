package com.kurierfree.server.domain.lesson.domain;

import com.kurierfree.server.domain.timeTable.domain.TimeTable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "lesson")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_table_id", nullable = false)
    private TimeTable timeTable;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String professor;

    @Column(nullable = false)
    private String classDay;

    @Column(nullable = false)
    private String startTime;

    @Column(nullable = false)
    private String endTime;

    @Builder
    private Lesson(TimeTable timeTable, String subject, String professor, String classDay, String startTime, String endTime) {
        this.timeTable = timeTable;
        this.subject = subject;
        this.professor = professor;
        this.classDay = classDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Lesson of(String subject, String professor, String classDay, String startTime, String endTime) {
        return Lesson.builder()
                .subject(subject)
                .professor(professor)
                .classDay(classDay)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

}
