package com.kurierfree.server.domain.semester.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
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

    public Semester(int currentYear, SemesterTime currentSemesterTime) {
        this.year = currentYear;
        this.semesterTime = currentSemesterTime;
    }

    // 현재 시간을 기반 semester 를 생성
    public static Semester createCurrentSemester() {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        SemesterTime currentSemesterTime;

        if (currentDate.getMonthValue() <= 8){
            currentSemesterTime = SemesterTime.SPRING;
        } else{
            currentSemesterTime = SemesterTime.FALL;
        }
        return new Semester(currentYear, currentSemesterTime);
    }
}
