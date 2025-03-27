package com.kurierfree.server.domain.lesson.dao;

import com.kurierfree.server.domain.lesson.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByTimeTableId(Long timeTableId);
}
