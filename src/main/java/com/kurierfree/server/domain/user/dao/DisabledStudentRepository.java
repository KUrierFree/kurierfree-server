package com.kurierfree.server.domain.user.dao;

import com.kurierfree.server.domain.user.domain.DisabledStudent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisabledStudentRepository extends JpaRepository<DisabledStudent, Long> {
}
