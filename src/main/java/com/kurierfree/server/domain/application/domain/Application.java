package com.kurierfree.server.domain.application.domain;

import com.kurierfree.server.domain.application.domain.enums.ActivityType;
import com.kurierfree.server.domain.user.domain.enums.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
