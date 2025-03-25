package com.kurierfree.server.domain.semester.application;

import com.kurierfree.server.domain.semester.dao.SemesterRepository;
import com.kurierfree.server.domain.semester.domain.Semester;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
