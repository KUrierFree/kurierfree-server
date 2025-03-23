package com.kurierfree.server.domain.semester.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "semester")
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private SemesterTime semesterTime;

    private static Semester currentSemester;

    public Semester(int year, SemesterTime semesterTime) {
        this.year = year;
        this.semesterTime = semesterTime;
    }

    public static Semester getCurrentSemester() {
//        if (currentSemester == null) {
//            currentSemester = createCurrentSemester();
//        }
//        return currentSemester;
        return new Semester(2025, SemesterTime.first);
    }
}
