package com.kurierfree.server.domain.application.dto.request;

import com.kurierfree.server.domain.application.domain.ActivityPreference;
import com.kurierfree.server.domain.application.domain.enums.ActivityType;
import com.kurierfree.server.domain.user.domain.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationRequest {

    private String department;
    private int studentId;
    private String grade;
    private String name;
    private Gender gender;
    private String birthDate;
    private Boolean hasExperience;
    private String email;
    private String phoneNum;
    private ActivityType activityType;
    private String volunteerExperience;

    private List<ActivityPreferenceDto> activityPreference;

    @Getter
    public static class ActivityPreferenceDto {
        private int priority;
        private String preferredActivity;
        private String availableTime;
    }

}
