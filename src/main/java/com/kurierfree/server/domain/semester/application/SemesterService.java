package com.kurierfree.server.domain.semester.application;

import com.kurierfree.server.domain.semester.dao.SemesterRepository;
import com.kurierfree.server.domain.semester.domain.Semester;
import com.kurierfree.server.domain.semester.dto.request.RecruitmentPeriodRequest;
import com.kurierfree.server.domain.semester.dto.response.RecruitmentPeriodResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class SemesterService {
    private final SemesterRepository semesterRepository;

    public SemesterService(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    @Transactional
    public Semester createAndSaveCurrentSemester(){
        Semester newSemester = Semester.createCurrentSemester();
        return semesterRepository.save(newSemester);
    }

    @Transactional
    public Semester getCurrentSemester(){
        Semester newSemester = Semester.createCurrentSemester();
        Semester findSemester = semesterRepository.findByYearAndSemesterTime(newSemester.getYear(), newSemester.getSemesterTime());
        return (findSemester != null) ? findSemester : createAndSaveCurrentSemester();
    }

    @Transactional
    public void setApplicationPeriod(Long semesterId, RecruitmentPeriodRequest recruitmentPeriodRequest) {
        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new EntityNotFoundException("Semester not found with id: " + semesterId));

        semester.updateApplicationPeriod(
                recruitmentPeriodRequest.getStartDate(),
                recruitmentPeriodRequest.getEndDate()
        );
    }

    @Transactional
    public void setSelectionPeriod(Long semesterId, RecruitmentPeriodRequest recruitmentPeriodRequest) {
        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new EntityNotFoundException("Semester not found with id: " + semesterId));

        semester.updateSelectionPeriod(
                recruitmentPeriodRequest.getStartDate(),
                recruitmentPeriodRequest.getEndDate()
        );
    }

    @Transactional
    public RecruitmentPeriodResponse getRecruitmentPeriod(Long semesterId) {
        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new EntityNotFoundException("Semester not found with id: " + semesterId));

        return RecruitmentPeriodResponse.from(semester, LocalDate.now());
    }
}
