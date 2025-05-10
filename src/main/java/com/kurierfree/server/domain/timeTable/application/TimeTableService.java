package com.kurierfree.server.domain.timeTable.application;

import com.kurierfree.server.domain.auth.infra.JwtProvider;
import com.kurierfree.server.domain.lesson.dao.LessonRepository;
import com.kurierfree.server.domain.lesson.domain.Lesson;
import com.kurierfree.server.domain.lesson.dto.response.LessonResponse;
import com.kurierfree.server.domain.lesson.dto.response.LessonScheduleResponse;
import com.kurierfree.server.domain.semester.application.SemesterService;
import com.kurierfree.server.domain.timeTable.dao.TimeTableRepository;
import com.kurierfree.server.domain.timeTable.domain.TimeTableLesson;
import com.kurierfree.server.domain.timeTable.dto.response.TimeTableResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TimeTableService {
    private final TimeTableRepository timeTableRepository;
    private final LessonRepository lessonRepository;
    private final SemesterService semesterService;
    private final JwtProvider jwtProvider;

    public TimeTableService(TimeTableRepository timeTableRepository, LessonRepository lessonRepository, SemesterService semesterService, JwtProvider jwtProvider) {
        this.timeTableRepository = timeTableRepository;
        this.lessonRepository = lessonRepository;
        this.semesterService = semesterService;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public TimeTableResponse getTimeTableWithUserId(Long userId) {
        return getTimeTableResponse(userId);
    }

    @Transactional
    public TimeTableResponse getTimeTableWithToken(String token) {
        String jwtToken = token.substring(7);
        Long userId = jwtProvider.getUserIdFromToken(jwtToken);

        return getTimeTableResponse(userId);
    }

    private TimeTableResponse getTimeTableResponse(Long userId) {
        List<TimeTableLesson> timeTableLessons= timeTableRepository.findLessonsByUserIdAndSemesterId(
                userId, semesterService.getCurrentSemester().getId()
        );
        if (timeTableLessons == null) {
            throw new EntityNotFoundException("TimeTable not found for userId: " + userId);
        }

        List<LessonResponse> lessonResponseList = timeTableLessons
                .stream()
                .map(timeTableLesson -> {
                    List<LessonScheduleResponse> scheduleResponses = timeTableLesson.getLesson().getLessonSchedules()
                            .stream()
                            .map(LessonScheduleResponse::from)
                            .toList();
                    return LessonResponse.from(timeTableLesson.getLesson(), scheduleResponses);
                })
                .toList();

        return TimeTableResponse.builder()
                .lessons(lessonResponseList)
                .build();
    }

    public int compareTimeTableScore(Long disabledStudentId, Long supporterId) {
        int score = 0;

        List<TimeTableLesson> disabledTimeTableLessons = timeTableRepository.findLessonsByUserIdAndSemesterId(
                disabledStudentId, semesterService.getCurrentSemester().getId()
        );

        List<TimeTableLesson> supporterTimeTableLessons = timeTableRepository.findLessonsByUserIdAndSemesterId(
                supporterId, semesterService.getCurrentSemester().getId()
        );

        if (disabledTimeTableLessons.isEmpty() || supporterTimeTableLessons.isEmpty()) {
            return score;
        }

        Set<Long> supporterLessonIds = supporterTimeTableLessons.stream()
                .map( timeTableLesson -> timeTableLesson.getLesson().getId())
                .collect(Collectors.toSet());

        Map<String, String> supporterSubjectsAndProfessor = supporterTimeTableLessons.stream()
                .map(TimeTableLesson::getLesson)
                .collect(Collectors.toMap(Lesson::getSubject, Lesson::getProfessor));

        for (TimeTableLesson disabledTimeTableLesson : disabledTimeTableLessons) {

            // 1. 같은 수업 ID (완전 동일 수업)
            if (supporterLessonIds.contains(disabledTimeTableLesson.getLesson().getId())) {
                score += 10;
            }
            // 수업명이 같을때 (분반이 다르면 다른 LessonId를 가짐)
            else if (supporterSubjectsAndProfessor.containsKey(disabledTimeTableLesson.getLesson().getSubject())) {

                // 2. 수업 명과 교수가 같을때 = 동일 수업 다른 분반
                if (disabledTimeTableLesson.getLesson().getProfessor().equals(
                        supporterSubjectsAndProfessor.get(disabledTimeTableLesson.getLesson().getSubject()))
                ) {
                    score += 7;
                }
                // 3. 수업 명은 같지만 다른 교수일때
                else {
                    score += 5;
                }
            }
        }
        return score;
    }

}
