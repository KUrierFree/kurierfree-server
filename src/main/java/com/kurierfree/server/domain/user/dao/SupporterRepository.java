package com.kurierfree.server.domain.user.dao;

import com.kurierfree.server.domain.list.dto.response.SupporterListItemResponse;
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
    WHERE s.status = com.kurierfree.server.domain.user.domain.enums.Status.MATCHED
    OR s.status = com.kurierfree.server.domain.user.domain.enums.Status.MATCHING
""")
    List<SupporterResponse> findAllForAdmin();

    @Query("""
        SELECT new com.kurierfree.server.domain.list.dto.response.SupporterListItemResponse(
            s.id, s.name, s.department, s.gender, s.grade, s.status
        )
        FROM Supporter s
            WHERE s.status IN (
           com.kurierfree.server.domain.user.domain.enums.Status.MATCHING
       )
    """)
    List<SupporterListItemResponse> findMatchingSupportersForAdmin();

    @Query("""
        SELECT new com.kurierfree.server.domain.list.dto.response.SupporterListItemResponse(
            s.id, s.name, s.department, s.gender, s.grade, s.status
        )
        FROM Supporter s
            WHERE s.status IN (
           com.kurierfree.server.domain.user.domain.enums.Status.PENDING,
           com.kurierfree.server.domain.user.domain.enums.Status.REJECTED,
           com.kurierfree.server.domain.user.domain.enums.Status.MATCHING
       )
    """)
    List<SupporterListItemResponse> findAppliedSupportersForAdmin();

    List<Supporter> findByStatus(Status status);

}
