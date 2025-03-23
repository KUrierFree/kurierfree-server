package com.kurierfree.server.domain.user.domain;

import com.kurierfree.server.domain.semester.domain.Semester;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "supporter")
public class Supporter extends User{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

    @Column(nullable = false)
    private ApplicationStatus status = ApplicationStatus.PENDING;

    public Supporter(int studentId, String name, String department, Gender gender, String grade, String password, Role role, Semester semester) {
        super(studentId, name, department, gender, grade, password, role);
        this.semester = semester;
    }
}
