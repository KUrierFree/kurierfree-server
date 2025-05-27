package com.kurierfree.server.domain.application.domain;

import jakarta.persistence.Embeddable;
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
    private String availableTime;
}
