package ru.yandex.practicum.gym;

import java.util.Objects;

public class Group {
    //название группы
    private String title;
    //тип (взрослая или детская)
    private Age age;
    //длительность (в минутах)
    private int duration;

    public Group(String title, Age age, int duration) {
        this.title = validateTitle(title);
        this.age = Objects.requireNonNull(age, "Age must not be empty");
        this.duration = validateDuration(duration);
    }

    public String getTitle() {
        return title;
    }

    public Age getAge() {
        return age;
    }

    public int getDuration() {
        return duration;
    }

    private String validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title must not be empty");
        }

        return title;
    }

    private int validateDuration(int duration) {
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be greater than 0");
        }
        return duration;
    }
}
