package com.kurierfree.server.domain.user.dto.request;

import com.kurierfree.server.domain.user.domain.enums.DisabilityType;
import com.kurierfree.server.domain.user.domain.enums.Gender;
import com.kurierfree.server.domain.user.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserRegisterRequest {
    private int studentId;
    private String name;
    private String department;
    private Gender gender;
    private String grade;
    private String password;
    private Role role;
    private DisabilityType disabilityType;
}




