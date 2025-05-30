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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;

    @Column(nullable = false)
    private int supporterMatchCount = 0;

    public Supporter(int studentId, String name, String department, Gender gender, String grade, String password, Role role) {
        super(studentId, name, department, gender, grade, password, role);
    }

    public void updateStatus(Status status) {
        this.status = status;
    }

    public void changeToRejected() {
        this.status = Status.REJECTED;
    }

    public void updateStatusMatched() {
        this.status = Status.MATCHED;
    }

    public boolean updateAndValidSupporterMatchCount() {
        this.supporterMatchCount = supporterMatchCount + 1;
        return supporterMatchCount < 2;
    }
}
