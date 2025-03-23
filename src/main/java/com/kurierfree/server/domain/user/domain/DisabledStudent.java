package com.kurierfree.server.domain.user.domain;

import com.kurierfree.server.domain.semester.domain.Semester;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "disabled_student")
public class DisabledStudent extends User{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

    @Column(nullable = false)
    private DisabilityType disabilityType;

    private String specialRequirements;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preferred_supporter_id")
    private Supporter preferredSupporter;

    public DisabledStudent(int studentId, String name, String department, Gender gender, String grade, String password, Role role, Semester semester, DisabilityType disabilityType) {
        super(studentId, name, department, gender, grade, password, role);
        this.semester = semester;
        this.disabilityType = disabilityType;
    }
}
