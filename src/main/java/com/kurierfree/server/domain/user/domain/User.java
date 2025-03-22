package com.kurierfree.server.domain.user.domain;

import com.kurierfree.server.domain.user.dto.request.UserRegisterRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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

    public static User from(UserRegisterRequest userRegisterRequest) {
        return User.builder()
                .studentId(userRegisterRequest.getStudentId())
                .name(userRegisterRequest.getName())
                .department(userRegisterRequest.getDepartment())
                .gender(userRegisterRequest.getGender())
                .grade(userRegisterRequest.getGrade())
                .password(userRegisterRequest.getPassword())
                .role(userRegisterRequest.getRole())
                .build();
    }
}
