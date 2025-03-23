package com.kurierfree.server.domain.semester.dao;

import com.kurierfree.server.domain.semester.domain.Semester;
import com.kurierfree.server.domain.semester.domain.SemesterTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<Semester, Long> {
    Semester findByYearAndSemesterTime(int currentYear, SemesterTime currentSemesterTime);
}
