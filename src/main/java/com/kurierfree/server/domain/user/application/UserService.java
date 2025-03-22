package com.kurierfree.server.domain.user.application;

import com.kurierfree.server.domain.user.dao.DisabledStudentRepository;
import com.kurierfree.server.domain.user.dao.SupporterRepository;
import com.kurierfree.server.domain.user.dao.UserRepository;
import com.kurierfree.server.domain.user.domain.User;
import com.kurierfree.server.domain.user.dto.request.UserRegisterRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SupporterRepository supporterRepository;
    private final DisabledStudentRepository disabledStudentRepository;

    public UserService(UserRepository userRepository, SupporterRepository supporterRepository, DisabledStudentRepository disabledStudentRepository) {
        this.userRepository = userRepository;
        this.supporterRepository = supporterRepository;
        this.disabledStudentRepository = disabledStudentRepository;
    }

    @Transactional
    public Long register(UserRegisterRequest userRegisterRequest) {
        User newUser = User.from(userRegisterRequest);
        User savedUser = userRepository.save(newUser);
        return savedUser.getId();
    }

}
