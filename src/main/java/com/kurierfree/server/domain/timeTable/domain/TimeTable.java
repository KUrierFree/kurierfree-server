package com.kurierfree.server.domain.timeTable.domain;

import com.kurierfree.server.domain.semester.domain.Semester;
import com.kurierfree.server.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "time_table")
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
}
