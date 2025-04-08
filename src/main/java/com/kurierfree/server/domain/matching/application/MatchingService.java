package com.kurierfree.server.domain.matching.application;

import com.kurierfree.server.domain.matching.dao.MatchingRepository;
import com.kurierfree.server.domain.matching.domain.Matching;
import com.kurierfree.server.domain.matching.dto.request.MatchingRequest;
import com.kurierfree.server.domain.matching.dto.response.MatchingResponse;
import com.kurierfree.server.domain.semester.application.SemesterService;
import com.kurierfree.server.domain.user.dao.DisabledStudentRepository;
import com.kurierfree.server.domain.user.dao.SupporterRepository;
import com.kurierfree.server.domain.user.dao.UserRepository;
import com.kurierfree.server.domain.user.domain.DisabledStudent;
import com.kurierfree.server.domain.user.domain.Supporter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchingService {
    private final MatchingRepository matchingRepository;
    private final SupporterRepository supporterRepository;
    private final DisabledStudentRepository disabledStudentRepository;
    private final SemesterService semesterService;

    public MatchingService(MatchingRepository matchingRepository, SupporterRepository supporterRepository, DisabledStudentRepository disabledStudentRepository, SemesterService semesterService) {
        this.matchingRepository = matchingRepository;
        this.supporterRepository = supporterRepository;
        this.disabledStudentRepository = disabledStudentRepository;
        this.semesterService = semesterService;
    }

    @Transactional
    public void createMatching(MatchingRequest matchingRequest) {
        Supporter supporter = supporterRepository.findById(matchingRequest.getSupporterId())
                .orElseThrow(() -> new EntityNotFoundException("해당 아이디를 가진 서포터즈가 존재하지 않습니다."));

        DisabledStudent disabledStudent = disabledStudentRepository.findById(matchingRequest.getDisabledStudentId())
                .orElseThrow(() -> new EntityNotFoundException("해당 아이디를 가진 장애학생이 존재하지 않습니다."));

        Matching matching = Matching.builder()
                .semester(semesterService.getCurrentSemester())
                .supporter(supporter)
                .disabledStudent(disabledStudent)
                .build();

        matchingRepository.save(matching);

        supporter.updateStatusMatched();
        disabledStudent.updateStatusMatched();
    }

    public MatchingResponse getMatchingThirdPriorityOfDisabledStudent(Long disabledStudentsId) {
        return null;
    }
}
