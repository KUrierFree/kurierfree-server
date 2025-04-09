package com.kurierfree.server.domain.user.dao;

import com.kurierfree.server.domain.list.dto.response.SupporterResponse;
import com.kurierfree.server.domain.user.domain.Supporter;
import com.kurierfree.server.domain.user.domain.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SupporterRepository extends JpaRepository<Supporter, Long> {

    @Query("""
    SELECT new com.kurierfree.server.domain.list.dto.response.SupporterResponse(
        s.id, s.name, s.department, s.gender,
        CASE WHEN s.status = com.kurierfree.server.domain.user.domain.enums.Status.MATCHED THEN true ELSE false END
    )
    FROM Supporter s
""")
    List<SupporterResponse> findAllForAdmin();

    @Query("SELECT d.id FROM Supporter d")
    List<Long> findAllIds(); // 모든 장애학생 ID만 추출
}
