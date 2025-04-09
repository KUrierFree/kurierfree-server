package com.kurierfree.server.domain.user.domain;

import com.kurierfree.server.domain.user.domain.enums.Status;
import com.kurierfree.server.domain.user.domain.enums.Gender;
import com.kurierfree.server.domain.user.domain.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "supporter")
public class Supporter extends User{

    @Column(nullable = false)
    private Status status = Status.PENDING;

    @Column(nullable = false)
    private int supporterMatchCount = 0;

    public Supporter(int studentId, String name, String department, Gender gender, String grade, String password, Role role) {
        super(studentId, name, department, gender, grade, password, role);
    }

    public void updateStatusMatched() {
        this.status = Status.MATCHED;
    }

    public int updateAndGetSupporterMatchCount() {
        this.supporterMatchCount += 1;
        return this.supporterMatchCount;
    }
}
