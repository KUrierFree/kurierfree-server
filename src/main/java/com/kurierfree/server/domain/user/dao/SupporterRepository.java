package com.kurierfree.server.domain.user.dao;

import com.kurierfree.server.domain.list.dto.response.SupporterResponse;
import com.kurierfree.server.domain.user.domain.Supporter;
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

}
