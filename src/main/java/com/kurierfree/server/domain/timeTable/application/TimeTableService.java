package com.kurierfree.server.domain.timeTable.application;

import com.kurierfree.server.domain.auth.infra.JwtProvider;
import com.kurierfree.server.domain.lesson.dao.LessonRepository;
import com.kurierfree.server.domain.lesson.dto.response.LessonResponse;
import com.kurierfree.server.domain.semester.application.SemesterService;
import com.kurierfree.server.domain.timeTable.dao.TimeTableRepository;
import com.kurierfree.server.domain.timeTable.domain.TimeTable;
import com.kurierfree.server.domain.timeTable.dto.response.TimeTableResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public TimeTableResponse getTimeTable(String token) {
        String jwtToken = token.substring(7);
        Long userId = jwtProvider.getUserIdFromToken(jwtToken);

        TimeTable timeTable= timeTableRepository.findByUserIdAndSemesterId(
                userId, semesterService.getCurrentSemester().getId()
        );
        if (timeTable == null) {
            throw new EntityNotFoundException("TimeTable not found for userId: " + userId);

        }

        List<LessonResponse> lessonResponseList = lessonRepository.findByTimeTableId(timeTable.getId())
                .stream()
                .map(LessonResponse::from)
                .toList();

        return TimeTableResponse.builder()
                .lessons(lessonResponseList)
                .build();
    }

}
