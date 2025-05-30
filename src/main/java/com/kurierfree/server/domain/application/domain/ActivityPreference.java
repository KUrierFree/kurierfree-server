package com.kurierfree.server.domain.application.domain;

import com.kurierfree.server.domain.lesson.domain.ClassDay;
import com.kurierfree.server.domain.lesson.domain.ClassTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ActivityPreference {
    private int priority;
    private String preferredActivity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClassDay classDay;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "hour", column = @Column(name = "start_hour", nullable = false)),
            @AttributeOverride(name = "minute", column = @Column(name = "start_minute", nullable = false))
    })
    private ClassTime startTime;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "hour", column = @Column(name = "end_hour", nullable = false)),
            @AttributeOverride(name = "minute", column = @Column(name = "end_minute", nullable = false))
    })
    private ClassTime endTime;
}
