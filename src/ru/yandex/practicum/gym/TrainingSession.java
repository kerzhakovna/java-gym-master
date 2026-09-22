package ru.yandex.practicum.gym;

import java.util.Objects;

public class TrainingSession {

    //группа
    private Group group;
    //тренер
    private Coach coach;
    //день недели
    private DayOfWeek dayOfWeek;
    //время начала занятия
    private TimeOfDay timeOfDay;

    public TrainingSession(Group group, Coach coach, DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        this.group = Objects.requireNonNull(group, "Group must not be null");
        this.coach = Objects.requireNonNull(coach, "Coach must not be null");
        this.dayOfWeek = Objects.requireNonNull(dayOfWeek, "DayOfWeek must not be null");
        this.timeOfDay = Objects.requireNonNull(timeOfDay, "TimeOfDay must not be null");
    }

    public Group getGroup() {
        return group;
    }

    public Coach getCoach() {
        return coach;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public TimeOfDay getTimeOfDay() {
        return timeOfDay;
    }
}
