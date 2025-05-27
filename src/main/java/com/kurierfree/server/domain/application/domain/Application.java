package com.kurierfree.server.domain.application.domain;

import com.kurierfree.server.domain.application.domain.enums.ActivityType;
import com.kurierfree.server.domain.application.dto.request.ApplicationRequest;
import com.kurierfree.server.domain.user.domain.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private int studentId;

    @Column(nullable = false)
    private String grade; // 4-1

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private String birthDate;

    @Column(nullable = false)
    private Boolean hasExperience;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNum;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType activityType;

    @Column(nullable = false)
    private String volunteerExperience;

    @ElementCollection
    @CollectionTable(name = "activity_preferences", joinColumns = @JoinColumn(name = "application_id"))
    @OrderBy("priority ASC")
    private List<ActivityPreference> activityPreference = new ArrayList<>();

    @Builder
    public Application(String department, int studentId, String grade, String name,
                       Gender gender, String birthDate, Boolean hasExperience, String email,
                       String phoneNum, ActivityType activityType, String volunteerExperience) {
        this.department = department;
        this.studentId = studentId;
        this.grade = grade;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.hasExperience = hasExperience;
        this.email = email;
        this.phoneNum = phoneNum;
        this.activityType = activityType;
        this.volunteerExperience = volunteerExperience;
        this.activityPreference = new ArrayList<>();
    }

    public static Application of(ApplicationRequest request) {
        return Application.builder()
                .department(request.getDepartment())
                .studentId(request.getStudentId())
                .grade(request.getGrade())
                .name(request.getName())
                .gender(request.getGender())
                .birthDate(request.getBirthDate())
                .hasExperience(request.getHasExperience())
                .email(request.getEmail())
                .phoneNum(request.getPhoneNum())
                .activityType(request.getActivityType())
                .volunteerExperience(request.getVolunteerExperience())
                .build();
    }
}
