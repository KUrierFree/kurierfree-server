package com.kurierfree.server.domain.user.dao;

import com.kurierfree.server.domain.user.domain.Supporter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupprterRepository extends JpaRepository<Supporter, Long> {
}
