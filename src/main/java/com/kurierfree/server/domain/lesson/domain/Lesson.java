package com.kurierfree.server.domain.lesson.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "lesson")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String professor;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LessonSchedule> lessonSchedules = new ArrayList<>();

    @Builder
    private Lesson(String subject, String professor) {
        this.subject = subject;
        this.professor = professor;
    }

    public static Lesson of(String subject, String professor) {
        return Lesson.builder()
                .subject(subject)
                .professor(professor)
                .build();
    }

    public void addLessonSchedules(LessonSchedule lessonSchedule) {
        this.lessonSchedules.add(lessonSchedule);
        lessonSchedule.setLesson(this);
    }
}
