package com.kurierfree.server.domain.list.application;

import com.kurierfree.server.domain.auth.infra.JwtProvider;
import com.kurierfree.server.domain.list.dto.response.DisabledStudentResponse;
import com.kurierfree.server.domain.list.dto.response.SupporterResponse;
import com.kurierfree.server.domain.user.dao.DisabledStudentRepository;
import com.kurierfree.server.domain.user.dao.SupporterRepository;
import com.kurierfree.server.domain.user.dao.UserRepository;
import com.kurierfree.server.domain.user.domain.User;
import com.kurierfree.server.domain.user.domain.enums.Role;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListService {
    private final JwtProvider jwtProvider;
    private final DisabledStudentRepository disabledStudentRepository;
    private final SupporterRepository supporterRepository;
    private final UserRepository userRepository;

    public ListService(JwtProvider jwtProvider, DisabledStudentRepository disabledStudentRepository, SupporterRepository supporterRepository, UserRepository userRepository) {
        this.jwtProvider = jwtProvider;
        this.disabledStudentRepository = disabledStudentRepository;
        this.supporterRepository = supporterRepository;
        this.userRepository = userRepository;
    }

    public List<DisabledStudentResponse> getDisabledStudentsList(String token){
        checkRoleIsAdmin(token);

        return disabledStudentRepository.findAllForAdmin();
    }


    public List<SupporterResponse> getSupportersList(String token) {
        checkRoleIsAdmin(token);

        return supporterRepository.findAllForAdmin();
    }

    private void checkRoleIsAdmin(String token) {
        String jwtToken = token.substring(7);
        Long userId = jwtProvider.getUserIdFromToken(jwtToken);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        if (user.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("관리자만 접근할 수 있습니다.");
        }
    }
}
