package com.kurierfree.server.domain.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "supporter")
public class Supporter extends User{

    @Column(nullable = false)
    private ApplicationStatus status;

}
