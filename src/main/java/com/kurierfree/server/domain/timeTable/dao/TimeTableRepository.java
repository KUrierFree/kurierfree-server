package com.kurierfree.server.domain.timeTable.dao;

import com.kurierfree.server.domain.timeTable.domain.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {
}
