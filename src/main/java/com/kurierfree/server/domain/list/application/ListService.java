package com.kurierfree.server.domain.list.application;

import com.kurierfree.server.domain.auth.infra.JwtProvider;
import com.kurierfree.server.domain.list.dto.response.DisabledStudentsResponse;
import com.kurierfree.server.domain.user.dao.DisabledStudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListService {
    private final JwtProvider jwtProvider;
    private final DisabledStudentRepository disabledStudentRepository;
    public ListService(JwtProvider jwtProvider, DisabledStudentRepository disabledStudentRepository) {
        this.jwtProvider = jwtProvider;
        this.disabledStudentRepository = disabledStudentRepository;
    }

    public List<DisabledStudentsResponse> getDisabledStudentsList(String token){
        String jwtToken = token.substring(7);
        Long userId = jwtProvider.getUserIdFromToken(jwtToken);

        return disabledStudentRepository.findAllForAdmin();
    }


}
