package com.kurierfree.server.domain.matching.application;

import com.kurierfree.server.domain.matching.dao.MatchingRepository;
import com.kurierfree.server.domain.matching.dto.request.MatchingRequest;
import com.kurierfree.server.domain.matching.dto.response.MatchingResponse;
import com.kurierfree.server.domain.user.dao.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchingService {
    private final MatchingRepository matchingRepository;
    private final UserRepository userRepository;

    public MatchingService(MatchingRepository matchingRepository, UserRepository userRepository) {
        this.matchingRepository = matchingRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createMatching(MatchingRequest matchingRequest) {
    }

    public MatchingResponse getMatchingThirdPriorityOfDisabledStudent(Long disabledStudentsId) {
        return null;
    }
}
