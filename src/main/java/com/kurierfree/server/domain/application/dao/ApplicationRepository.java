package com.kurierfree.server.domain.application.dao;

import com.kurierfree.server.domain.application.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Application findByStudentId(int studentId);
}
