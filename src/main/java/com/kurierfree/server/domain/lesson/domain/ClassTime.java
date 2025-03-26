package com.kurierfree.server.domain.lesson.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class ClassTime {
    private final int hour;
    private final int minute;

    private ClassTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public static ClassTime of(int hour, int minute) {
        validClassTime(hour, minute);
        return new ClassTime(hour, minute);
    }

    private static void validClassTime(int hour, int minute) {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Hour must be between 0 and 23");
        }
        if (minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Minute must be between 0 and 59");
        }
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", hour, minute);
    }

}
