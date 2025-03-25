package com.kurierfree.server.domain.user.application;

import com.kurierfree.server.domain.auth.domain.JwtToken;
import com.kurierfree.server.domain.auth.infra.JwtGenerator;
import com.kurierfree.server.domain.auth.infra.JwtProvider;
import com.kurierfree.server.domain.semester.application.SemesterService;
import com.kurierfree.server.domain.user.dao.DisabledStudentRepository;
import com.kurierfree.server.domain.user.dao.SupporterRepository;
import com.kurierfree.server.domain.user.dao.UserRepository;
import com.kurierfree.server.domain.user.domain.DisabledStudent;
import com.kurierfree.server.domain.user.domain.enums.Role;
import com.kurierfree.server.domain.user.domain.Supporter;
import com.kurierfree.server.domain.user.domain.User;
import com.kurierfree.server.domain.user.dto.request.UserLoginRequest;
import com.kurierfree.server.domain.user.dto.request.UserRegisterRequest;
import com.kurierfree.server.domain.user.dto.response.UserLoginResponse;
import com.kurierfree.server.domain.user.dto.response.UserRegisterResponse;
import com.kurierfree.server.domain.user.dto.response.UserResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final SupporterRepository supporterRepository;
    private final DisabledStudentRepository disabledStudentRepository;
    private final SemesterService semesterService;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;
    private final JwtProvider jwtProvider;

    public UserService(SupporterRepository supporterRepository,
                       DisabledStudentRepository disabledStudentRepository,
                       SemesterService semesterService,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtGenerator jwtGenerator,
                       JwtProvider jwtProvider) {
        this.supporterRepository = supporterRepository;
        this.disabledStudentRepository = disabledStudentRepository;
        this.semesterService = semesterService;
        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest.getRole() == Role.SUPPORTER){
            return UserRegisterResponse.builder()
                    .message("회원가입이 완료되었습니다.")
                    .userId(registerSupporter(userRegisterRequest))
                    .build();
        } else{
            return UserRegisterResponse.builder()
                    .message("회원가입이 완료되었습니다.")
                    .userId(registerDisabledStudent(userRegisterRequest))
                    .build();
        }
    }

    private Long registerSupporter(UserRegisterRequest userRegisterRequest) {

        Supporter supporter = new Supporter(
                userRegisterRequest.getStudentId(),
                userRegisterRequest.getName(),
                userRegisterRequest.getDepartment(),
                userRegisterRequest.getGender(),
                userRegisterRequest.getGrade(),
                passwordEncoder.encode(userRegisterRequest.getPassword()),
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
                passwordEncoder.encode(userRegisterRequest.getPassword()),
                userRegisterRequest.getRole(),
                semesterService.getCurrentSemester(),
                userRegisterRequest.getDisabilityType()
        );
        DisabledStudent savedDisabledStudent = disabledStudentRepository.save(disabledStudent);
        return savedDisabledStudent.getId();
    }

    @Transactional
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        User user = userRepository.findByStudentId(userLoginRequest.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("해당 아이디를 가진 사용자가 존재하지 않습니다."));

        JwtToken jwtToken = jwtGenerator.generateToken(user.getId());

        if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())){
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        UserResponse userResponse = UserResponse.builder()
                .userId(user.getId())
                .role(user.getRole())
                .build();

        return UserLoginResponse.builder()
                .message("로그인에 성공하였습니다.")
                .user(userResponse)
                .token(jwtToken)
                .build();
    }

    @Transactional
    public Long logout(String token) {
        String jwtToken = token.substring(7);
        return jwtProvider.getUserIdFromToken(jwtToken);

    }
}
