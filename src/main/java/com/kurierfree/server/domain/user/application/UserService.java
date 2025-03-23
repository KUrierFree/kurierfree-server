package com.kurierfree.server.domain.user.application;

import com.kurierfree.server.domain.semester.application.SemesterService;
import com.kurierfree.server.domain.semester.domain.Semester;
import com.kurierfree.server.domain.user.dao.DisabledStudentRepository;
import com.kurierfree.server.domain.user.dao.SupporterRepository;
import com.kurierfree.server.domain.user.dao.UserRepository;
import com.kurierfree.server.domain.user.domain.DisabledStudent;
import com.kurierfree.server.domain.user.domain.Role;
import com.kurierfree.server.domain.user.domain.Supporter;
import com.kurierfree.server.domain.user.dto.request.UserRegisterRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final SupporterRepository supporterRepository;
    private final DisabledStudentRepository disabledStudentRepository;
    private final SemesterService semesterService;

    public UserService(SupporterRepository supporterRepository, DisabledStudentRepository disabledStudentRepository, SemesterService semesterService) {
        this.supporterRepository = supporterRepository;
        this.disabledStudentRepository = disabledStudentRepository;
        this.semesterService = semesterService;
    }

    @Transactional
    public Long register(UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest.getRole() == Role.SUPPORTER){
            return registerSupporter(userRegisterRequest);
        } else{
            return registerDisabledStudent(userRegisterRequest);
        }
    }

    private Long registerSupporter(UserRegisterRequest userRegisterRequest) {
        Supporter supporter = new Supporter(
                userRegisterRequest.getStudentId(),
                userRegisterRequest.getName(),
                userRegisterRequest.getDepartment(),
                userRegisterRequest.getGender(),
                userRegisterRequest.getGrade(),
                userRegisterRequest.getPassword(),
                userRegisterRequest.getRole(),
                semesterService.getCurrentSemester()
        );
        Supporter savedSupporter = supporterRepository.save(supporter);
        return savedSupporter.getId();
    }

    private Long registerDisabledStudent(UserRegisterRequest userRegisterRequest) {
        DisabledStudent disabledStudent = new DisabledStudent(
                userRegisterRequest.getStudentId(),
                userRegisterRequest.getName(),
                userRegisterRequest.getDepartment(),
                userRegisterRequest.getGender(),
                userRegisterRequest.getGrade(),
                userRegisterRequest.getPassword(),
                userRegisterRequest.getRole(),
                semesterService.getCurrentSemester(),
                userRegisterRequest.getDisabilityType()
        );
        DisabledStudent savedDisabledStudent = disabledStudentRepository.save(disabledStudent);
        return savedDisabledStudent.getId();
    }

}
