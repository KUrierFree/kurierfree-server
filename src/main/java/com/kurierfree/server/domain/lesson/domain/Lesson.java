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
    private ClassDay classDay;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "hour", column = @Column(name = "start_hour", nullable = false)),
            @AttributeOverride(name = "minute", column = @Column(name = "start_minute", nullable = false))
    })
    private ClassTime startTime;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "hour", column = @Column(name = "end_hour", nullable = false)),
            @AttributeOverride(name = "minute", column = @Column(name = "end_minute", nullable = false))
    })
    private ClassTime endTime;

    @Builder
    private Lesson(TimeTable timeTable, String subject, String professor, ClassDay classDay, ClassTime startTime, ClassTime endTime) {
        this.timeTable = timeTable;
        this.subject = subject;
        this.professor = professor;
        this.classDay = classDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Lesson of(String subject, String professor, ClassDay classDay, ClassTime startTime, ClassTime endTime) {
        return Lesson.builder()
                .subject(subject)
                .professor(professor)
                .classDay(classDay)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

}
