package com.kurierfree.server.domain.matching.domain;

import com.kurierfree.server.domain.semester.domain.Semester;
import com.kurierfree.server.domain.user.domain.DisabledStudent;
import com.kurierfree.server.domain.user.domain.Supporter;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "matching")
public class Matching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supporter_id", nullable = false)
    private Supporter supporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disabled_student_id", nullable = false)
    private DisabledStudent disabledStudent;

    @Builder
    public Matching(Semester semester, Supporter supporter, DisabledStudent disabledStudent) {
        this.semester = semester;
        this.supporter = supporter;
        this.disabledStudent = disabledStudent;
    }
}
