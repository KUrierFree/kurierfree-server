package com.kurierfree.server.domain.user.domain;

import com.kurierfree.server.domain.semester.domain.Semester;
import com.kurierfree.server.domain.user.domain.enums.Status;
import com.kurierfree.server.domain.user.domain.enums.Gender;
import com.kurierfree.server.domain.user.domain.enums.Role;
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

    @Column(nullable = false)
    private Status status = Status.PENDING;

    public Supporter(int studentId, String name, String department, Gender gender, String grade, String password, Role role, Semester semester) {
        super(studentId, name, department, gender, grade, password, role, semester);
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
}
