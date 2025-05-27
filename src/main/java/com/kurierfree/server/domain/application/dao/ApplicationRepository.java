package com.kurierfree.server.domain.application.dao;

import com.kurierfree.server.domain.application.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
