package com.kurierfree.server.domain.matching.dao;

import com.kurierfree.server.domain.matching.domain.Matching;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingRepository extends JpaRepository<Matching, Long> {
}
