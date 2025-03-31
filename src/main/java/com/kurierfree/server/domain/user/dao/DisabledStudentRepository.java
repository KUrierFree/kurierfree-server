package com.kurierfree.server.domain.user.dao;

import com.kurierfree.server.domain.list.dto.response.DisabledStudentResponse;
import com.kurierfree.server.domain.user.domain.DisabledStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DisabledStudentRepository extends JpaRepository<DisabledStudent, Long> {
    @Query("""
    SELECT new com.kurierfree.server.domain.list.dto.response.DisabledStudentResponse(
        ds.id, ds.name, ds.department, ds.gender, ds.disabilityType,
        CASE WHEN ds.status = com.kurierfree.server.domain.user.domain.enums.Status.MATCHED THEN true ELSE false END
    )
    FROM DisabledStudent ds
""")
    List<DisabledStudentResponse> findAllForAdmin();
}
