package com.kurierfree.server.domain.matching.dao;

import com.kurierfree.server.domain.matching.domain.MatchingScoreCache;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MatchingScoreCacheRepository extends JpaRepository<MatchingScoreCache, Long> {
    List<MatchingScoreCache> findByDisabledStudentIdOrderByScoreDesc(Long disabledStudentId);
    void deleteBySupporterId(Long supporterId); // 매칭 확정된 서포터즈 제거 시 사용

    void deleteBySupporterIdAndDisabledStudentId(Long supporterId, Long disabledStudentId);

    MatchingScoreCache findByDisabledStudentIdAndSupporterId(Long disabledStudentId, Long supporterId);
    List<MatchingScoreCache> findBySupporterId(Long supporterId);

}
