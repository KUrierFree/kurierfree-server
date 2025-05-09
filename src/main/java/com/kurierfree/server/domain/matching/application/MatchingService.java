package com.kurierfree.server.domain.matching.application;

import com.kurierfree.server.domain.matching.dao.MatchingRepository;
import com.kurierfree.server.domain.matching.dao.MatchingScoreCacheRepository;
import com.kurierfree.server.domain.matching.domain.Matching;
import com.kurierfree.server.domain.matching.domain.MatchingScoreCache;
import com.kurierfree.server.domain.matching.dto.request.MatchingRequest;
import com.kurierfree.server.domain.matching.dto.response.MatchingResponse;
import com.kurierfree.server.domain.matching.dto.response.MatchingSupportersResponse;
import com.kurierfree.server.domain.semester.application.SemesterService;
import com.kurierfree.server.domain.timeTable.application.TimeTableService;
import com.kurierfree.server.domain.user.dao.DisabledStudentRepository;
import com.kurierfree.server.domain.user.dao.SupporterRepository;
import com.kurierfree.server.domain.user.domain.DisabledStudent;
import com.kurierfree.server.domain.user.domain.Supporter;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MatchingService {
    private final MatchingRepository matchingRepository;
    private final SupporterRepository supporterRepository;
    private final DisabledStudentRepository disabledStudentRepository;
    private final SemesterService semesterService;
    private final MatchingScoreCacheRepository matchingScoreCacheRepository;
    private final TimeTableService timeTableService;

    public MatchingService(
            MatchingRepository matchingRepository,
            SupporterRepository supporterRepository,
            DisabledStudentRepository disabledStudentRepository,
            SemesterService semesterService,
            MatchingScoreCacheRepository matchingScoreCacheRepository,
            TimeTableService timeTableService
    ) {
        this.matchingRepository = matchingRepository;
        this.supporterRepository = supporterRepository;
        this.disabledStudentRepository = disabledStudentRepository;
        this.semesterService = semesterService;
        this.matchingScoreCacheRepository = matchingScoreCacheRepository;
        this.timeTableService = timeTableService;
    }

    @Transactional
    public void createMatching(MatchingRequest matchingRequest) {
        Supporter supporter = supporterRepository.findById(matchingRequest.getSupporterId())
                .orElseThrow(() -> new EntityNotFoundException("해당 아이디를 가진 서포터즈가 존재하지 않습니다."));

        DisabledStudent disabledStudent = disabledStudentRepository.findById(matchingRequest.getDisabledStudentId())
                .orElseThrow(() -> new EntityNotFoundException("해당 아이디를 가진 장애학생이 존재하지 않습니다."));

        matchingRepository.save(Matching.of(
                semesterService.getCurrentSemester(),
                supporter,
                disabledStudent)
        );

        // 매칭이 완료된 장애학생-서포터즈 조합의 스코어 정보는 삭제
        matchingScoreCacheRepository.deleteBySupporterIdAndDisabledStudentId(supporter.getId(), disabledStudent.getId());

        // 서포터즈가 2명의 장애학생과 매칭되었다면 매칭완료로 상태변경
        if (!supporter.updateAndValidSupporterMatchCount()){
            supporter.updateStatusMatched();

            // 매칭이 끝난 서포터즈 -> 매칭 점수 계산 테이블에서 삭제
            matchingScoreCacheRepository.deleteBySupporterId(supporter.getId());
        }
        // Todo: 한 매칭이 끝나면 한 장애학생의 top3에만 유일하게 속하는 서포터즈가 있는지 확인


    }

    @Transactional
    public MatchingResponse getMatchingThirdPriorityOfDisabledStudent(Long disabledStudentsId) {
        // 장애학생의 매칭 score 를 기준으로 내림차순 정렬한 list
        List<MatchingScoreCache> matchingScoreCacheList = matchingScoreCacheRepository.findByDisabledStudentIdOrderByScoreDesc(disabledStudentsId);

        List<MatchingScoreCache> matchingSupportersList = new ArrayList<>();

        List<MatchingScoreCache> promotionStatusList = matchingScoreCacheList.stream()
                .filter(MatchingScoreCache::isPromotionStatus)
                .toList();

        if (!promotionStatusList.isEmpty()){
            matchingSupportersList.addAll(promotionStatusList);
        }
        matchingSupportersList.addAll(
                matchingScoreCacheList.stream()
                        .sorted(Comparator.comparingInt(MatchingScoreCache::getScore).reversed())
                        .limit(3 - promotionStatusList.size())
                        .toList()
        );

        List<MatchingSupportersResponse> matchingSupportersResponseList = matchingSupportersList.stream()
                .map(matchingScoreCache -> {
                    Supporter supporter = supporterRepository.findById(matchingScoreCache.getSupporterId())
                            .orElseThrow(() -> new EntityNotFoundException("해당 아이디를 가진 서포터즈가 존재하지 않습니다."));

                    return MatchingSupportersResponse.builder()
                            .rank(matchingSupportersList.indexOf(matchingScoreCache) + 1) // 현재 인덱스를 rank로 사용
                            .supporterId(supporter.getId())
                            .name(supporter.getName())
                            .department(supporter.getDepartment())
                            .gender(supporter.getGender())
                            .grade(supporter.getGrade())
                            .build();
                })
                .toList();

        return MatchingResponse.builder()
                .matchingSupporterResponses(matchingSupportersResponseList)
                .build();
    }

    // 매칭 스코어 계산 메소드
    private void saveMatchingScore(Long disabledStudentsId, Long supporterId) {
        DisabledStudent disabledStudent = disabledStudentRepository.findById(disabledStudentsId)
                .orElseThrow(() -> new EntityNotFoundException("해당 아이디를 가진 장애학생이 존재하지 않습니다."));

        Supporter supporter = supporterRepository.findById(supporterId)
                .orElseThrow(() -> new EntityNotFoundException("해당 아이디를 가진 서포터즈가 존재하지 않습니다."));

        int score = 0;

        // 사전 지정이라면 1순위로 만들기
        boolean isPreferredSupporter = disabledStudent.isPreferredSupporter(supporterId);

        // Todo: 장애학생 수업시간 = 서포터즈 활동 가능시간인가
        // 동일 과목 수강 여부
        int timeTableMatchScore = timeTableService.compareTimeTableScore(disabledStudentsId, supporterId);

        // 동일 성별 여부
        boolean genderMatch = (supporter.getGender() == disabledStudent.getGender());

        // 같은 학과 여부
        boolean departmentMatch = supporter.getDepartment().equals(disabledStudent.getDepartment());

        // 점수 계산
        if (isPreferredSupporter) score += 100;
        if (genderMatch) score += 3;
        if (departmentMatch) score += 1;
        score += timeTableMatchScore;

        MatchingScoreCache matchingScoreCache = MatchingScoreCache.of(
                disabledStudentsId,
                supporterId,
                score,
                genderMatch,
                departmentMatch,
                isPreferredSupporter,
                timeTableMatchScore
                );

        matchingScoreCacheRepository.save(matchingScoreCache);
    }

    // 매칭 score 저장: 선발기간 시작시기에 맞춰 1회만 실행
    @PostConstruct // Todo: 스케줄링으로 바꾸기
    public void initMatchingScoreRepository(){
        if (matchingScoreCacheRepository.count() != 0) {
            return;
        }

        for (Long disabledStudentId : disabledStudentRepository.findAllIds()) {
            for (Long supporterId : supporterRepository.findAllIds()) {
                saveMatchingScore(disabledStudentId, supporterId);
            }
        }
    }

}
