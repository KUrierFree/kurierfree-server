package com.kurierfree.server.domain.matching.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "matching_score_cache", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"disabledStudentId", "supporterId"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MatchingScoreCache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long disabledStudentId;

    private Long supporterId;

    private int score;

    private boolean genderMatch;

    private boolean departmentMatch;

    @Builder
    private MatchingScoreCache(Long disabledStudentId, Long supporterId, int score, boolean genderMatch, boolean departmentMatch) {
        this.disabledStudentId = disabledStudentId;
        this.supporterId = supporterId;
        this.score = score;
        this.genderMatch = genderMatch;
        this.departmentMatch = departmentMatch;
    }

    public static MatchingScoreCache of(Long disabledStudentId, Long supporterId, int score, boolean genderMatch, boolean departmentMatch) {
        return MatchingScoreCache.builder()
                .disabledStudentId(disabledStudentId)
                .supporterId(supporterId)
                .score(score)
                .genderMatch(genderMatch)
                .departmentMatch(departmentMatch)
                .build();
    }
}
