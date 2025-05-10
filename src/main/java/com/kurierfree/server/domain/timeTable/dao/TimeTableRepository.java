package com.kurierfree.server.domain.timeTable.dao;

import com.kurierfree.server.domain.timeTable.domain.TimeTable;
import com.kurierfree.server.domain.timeTable.domain.TimeTableLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {
    @Query("SELECT l FROM TimeTable t JOIN t.lessons l WHERE t.user.id = :userId and t.semester.id = :semesterId")
    List<TimeTableLesson> findLessonsByUserIdAndSemesterId(@Param("userId") Long userId, @Param("semesterId") Long semesterId);
}
