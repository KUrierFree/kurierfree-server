package com.kurierfree.server.domain.application.application;

import com.kurierfree.server.domain.application.dao.ApplicationRepository;
import com.kurierfree.server.domain.application.domain.ActivityPreference;
import com.kurierfree.server.domain.application.domain.Application;
import com.kurierfree.server.domain.application.dto.request.ApplicationRequest;
import com.kurierfree.server.domain.application.dto.response.ApplicationResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@AllArgsConstructor
@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;


    @Transactional
    public ApplicationResponse createApplication(ApplicationRequest applicationRequest) {

        Application application = Application.builder()
                .department(applicationRequest.getDepartment())
                .studentId(applicationRequest.getStudentId())
                .email(applicationRequest.getEmail())
                .name(applicationRequest.getName())
                .gender(applicationRequest.getGender())
                .grade(applicationRequest.getGrade())
                .activityType(applicationRequest.getActivityType())
                .birthDate(applicationRequest.getBirthDate())
                .hasExperience(applicationRequest.getHasExperience())
                .phoneNum(applicationRequest.getPhoneNum())
                .volunteerExperience(applicationRequest.getVolunteerExperience())
                .build();

        if (applicationRequest.getActivityPreference() != null) {
            List<ActivityPreference> preferences = applicationRequest.getActivityPreference().stream()
                    .map(dto -> ActivityPreference.of(
                            dto.getPriority(),
                            dto.getPreferredActivity(),
                            dto.getClassDay(),
                            dto.getStartTime(),
                            dto.getEndTime()
                    ))
                    .toList();

            application.getActivityPreference().addAll(preferences);
        }

        Application save = applicationRepository.save(application);

        return ApplicationResponse.from(save);
    }
}
