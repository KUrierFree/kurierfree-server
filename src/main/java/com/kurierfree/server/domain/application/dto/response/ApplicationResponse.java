package com.kurierfree.server.domain.application.dto.response;

import com.kurierfree.server.domain.application.domain.Application;
import com.kurierfree.server.domain.application.dto.request.ApplicationRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ApplicationResponse {
    private Long id;
    private String department;
    private String name;
    private int studentId;

    public static ApplicationResponse from(Application application) {
        return ApplicationResponse.builder()
                .id(application.getId())
                .department(application.getDepartment())
                .name(application.getName())
                .studentId(application.getStudentId())
                .build();
    }


}
