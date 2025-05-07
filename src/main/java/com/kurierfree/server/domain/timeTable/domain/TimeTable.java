package com.kurierfree.server.domain.timeTable.domain;

import com.kurierfree.server.domain.semester.domain.Semester;
import com.kurierfree.server.domain.user.domain.User;
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
@Table(name = "time_table", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "semester_id"}))
public class TimeTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

    @OneToMany(mappedBy = "timeTable", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeTableLesson> lessons = new ArrayList<>();

    @Builder
    private TimeTable( User user, Semester semester) {
        this.user = user;
        this.semester = semester;
    }

    public static TimeTable of(User user, Semester semester) {
        return TimeTable.builder()
                .user(user)
                .semester(semester)
                .build();
    }

    public void addLesson(TimeTableLesson lesson) {
        lessons.add(lesson);
        lesson.setTimeTable(this);
    }

}
