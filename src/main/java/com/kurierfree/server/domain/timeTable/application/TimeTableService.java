package com.kurierfree.server.domain.timeTable.application;

import com.kurierfree.server.domain.auth.infra.JwtProvider;
import com.kurierfree.server.domain.lesson.dao.LessonRepository;
import com.kurierfree.server.domain.lesson.domain.Lesson;
import com.kurierfree.server.domain.lesson.dto.response.LessonResponse;
import com.kurierfree.server.domain.lesson.dto.response.LessonScheduleResponse;
import com.kurierfree.server.domain.semester.application.SemesterService;
import com.kurierfree.server.domain.timeTable.dao.TimeTableRepository;
import com.kurierfree.server.domain.timeTable.domain.TimeTable;
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
        TimeTable timeTable= timeTableRepository.findByUserIdAndSemesterId(
                userId, semesterService.getCurrentSemester().getId()
        );
        if (timeTable == null) {
            throw new EntityNotFoundException("TimeTable not found for userId: " + userId);

        }

        List<LessonResponse> lessonResponseList = lessonRepository.findByTimeTableId(timeTable.getId())
                .stream()
                .map(lesson -> {
                    List<LessonScheduleResponse> scheduleResponses = lesson.getLessonSchedules()
                            .stream()
                            .map(LessonScheduleResponse::from)
                            .toList();
                    return LessonResponse.from(lesson, scheduleResponses);
                })
                .toList();

        return TimeTableResponse.builder()
                .lessons(lessonResponseList)
                .build();
    }

    public int compareTimeTableScore(Long disabledStudentId, Long supporterId) {
        int score = 0;

        TimeTable disabledTimeTable = timeTableRepository.findByUserIdAndSemesterId(
                disabledStudentId, semesterService.getCurrentSemester().getId()
        );

        TimeTable supporterTimeTable = timeTableRepository.findByUserIdAndSemesterId(
                supporterId, semesterService.getCurrentSemester().getId()
        );

        if (disabledTimeTable == null || supporterTimeTable == null) {
            return score;
        }

        List<Lesson> disabledLessons = lessonRepository.findByTimeTableId(disabledTimeTable.getId());
        List<Lesson> supporterLessons = lessonRepository.findByTimeTableId(supporterTimeTable.getId());

        if (disabledLessons == null || supporterLessons == null) {
            return score;
        }

        Set<Long> supporterLessonIds = supporterLessons.stream()
                .map(Lesson::getId)
                .collect(Collectors.toSet());

        Map<String, String> supporterSubjectsAndProfessor = supporterLessons.stream()
                .collect(Collectors.toMap(Lesson::getSubject, Lesson::getProfessor));

        for (Lesson disabledLesson : disabledLessons) {
            // 같은 수업 ID (완전 동일 수업)
            if (supporterLessonIds.contains(disabledLesson.getId())) {
                score += 10;
            }
            // 수업명이 같을때
            else if (supporterSubjectsAndProfessor.containsKey(disabledLesson.getSubject())) {
                // 교수까지 같을때
                if (disabledLesson.getProfessor().equals(supporterSubjectsAndProfessor.get(disabledLesson.getSubject())))
                    score += 7;
                // 다른 교수일때
                else {
                    score += 5;
                }
            }
        }

        return score;
    }

}
