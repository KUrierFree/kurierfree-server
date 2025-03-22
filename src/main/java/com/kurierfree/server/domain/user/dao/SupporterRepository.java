package com.kurierfree.server.domain.user.dao;

import com.kurierfree.server.domain.user.domain.Supporter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupporterRepository extends JpaRepository<Supporter, Long> {
}
