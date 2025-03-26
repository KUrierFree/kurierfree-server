package com.kurierfree.server.domain.lesson.dao;

import com.kurierfree.server.domain.lesson.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
