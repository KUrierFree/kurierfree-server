package com.kurierfree.server.domain.user.domain;

import com.kurierfree.server.domain.semester.domain.Semester;
import com.kurierfree.server.domain.user.domain.enums.Gender;
import com.kurierfree.server.domain.user.domain.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private int studentId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private String grade;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

    public User(int studentId, String name, String department, Gender gender, String grade, String password, Role role, Semester semester) {
        this.studentId = studentId;
        this.name = name;
        this.department = department;
        this.grade = grade;
        this.gender = gender;
        this.password = password;
        this.role = role;
        this.semester = semester;
    }
}
