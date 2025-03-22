package com.kurierfree.server.domain.user.domain;

public enum DisabilityType {
    PHYSICAL_DISABILITY("지체장애"),
    HEARING_DISABILITY("청각장애"),
    VISUAL_DISABILITY("시각장애"),
    CEREBRAL_DISABILITY("뇌병변장애(간질)"),
    INTELLECTUAL_DISABILITY("지적장애(자폐)");

    private final String description;

    DisabilityType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
