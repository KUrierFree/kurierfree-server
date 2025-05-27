package com.kurierfree.server.domain.application.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class ActivityPreference {
    private int priority;
    private String preferredActivity;
    private String availableTime;
}
