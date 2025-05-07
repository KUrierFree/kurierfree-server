package com.kurierfree.server.domain.lesson.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LessonSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String classroom;

    @Enumerated(EnumType.STRING)
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

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Builder
    public LessonSchedule(String classroom, ClassDay classDay, ClassTime startTime, ClassTime endTime) {
        this.classroom = classroom;
        this.classDay = classDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static LessonSchedule of(String classroom, ClassDay classDay, ClassTime startTime, ClassTime endTime){
        return LessonSchedule.builder()
                .classroom(classroom)
                .classDay(classDay)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

}
