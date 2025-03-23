package com.kurierfree.server.domain.semester.application;

import com.kurierfree.server.domain.semester.dao.SemesterRepository;
import com.kurierfree.server.domain.semester.domain.Semester;
import com.kurierfree.server.domain.semester.domain.SemesterTime;
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
    public Semester createCurrentSemester(){
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        SemesterTime currentSemesterTime;

        if (currentDate.getMonthValue() <= 8){
            currentSemesterTime = SemesterTime.first;
        } else{
            currentSemesterTime = SemesterTime.second;
        }

        Semester newSemester = new Semester(currentYear, currentSemesterTime);
        return semesterRepository.save(newSemester);
    }

    @Transactional
    public Semester getCurrentSemester(){
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        SemesterTime currentSemesterTime;

        if (currentDate.getMonthValue() <= 8){
            currentSemesterTime = SemesterTime.first;
        } else{
            currentSemesterTime = SemesterTime.second;
        }

        Semester findSemester = semesterRepository.findByYearAndSemesterTime(currentYear, currentSemesterTime);
        return (findSemester != null) ? findSemester : createCurrentSemester();
    }
}
