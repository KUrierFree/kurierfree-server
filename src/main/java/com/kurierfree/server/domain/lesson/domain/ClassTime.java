package com.kurierfree.server.domain.lesson.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class ClassTime {
    private int hour;
    private int minute;

    // 임베디드 클래스를 사용할 때는 기본 생성자가 반드시 필요
    // JPA가 리플렉션(reflection)을 통해 객체를 생성할 수 있도록 하기 위해
    public ClassTime(){} // 기본 생성자 추가

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
