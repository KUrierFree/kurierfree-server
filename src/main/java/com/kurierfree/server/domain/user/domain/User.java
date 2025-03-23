package com.kurierfree.server.domain.user.domain;

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

    public User(int studentId, String name, String department, Gender gender, String grade, String password, Role role) {
        this.studentId = studentId;
        this.name = name;
        this.department = department;
        this.grade = grade;
        this.gender = gender;
        this.password = password;
        this.role = role;
    }
}
