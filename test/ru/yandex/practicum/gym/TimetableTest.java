package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        //Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        Map<TimeOfDay, List<TrainingSession>> thursdaySchedule = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(2, thursdaySchedule.size());

        List<TimeOfDay> thursdayTimeSessions = new ArrayList<>(thursdaySchedule.keySet());
        Assertions.assertEquals(mondayChildTrainingSession.getTimeOfDay(), thursdayTimeSessions.getFirst());
        Assertions.assertEquals(thursdayAdultTrainingSession.getTimeOfDay(), thursdayTimeSessions.getLast());
        // Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        List<TrainingSession> mondayAt13 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        Assertions.assertEquals(1, mondayAt13.size());
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        List<TrainingSession> mondayAt14 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        Assertions.assertEquals(0, mondayAt14.size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeMultipleSessions() {
        Timetable timetable = new Timetable();
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession firstTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession secondTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);

        List<TrainingSession> mondayAt13 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        Assertions.assertEquals(2, mondayAt13.size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeFromEmptyTable() {
        Timetable timetable = new Timetable();
        List<TrainingSession> schedule = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.TUESDAY, new TimeOfDay(13, 0));
        Assertions.assertEquals(0, schedule.size());
    }

    @Test
    void testCreateInvalidSession() {
        Timetable timetable = new Timetable();
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Assertions.assertThrows(NullPointerException.class, () -> new TrainingSession(group, coach, null, null));
        Assertions.assertThrows(NullPointerException.class, () -> timetable.addNewTrainingSession(null));
    }

    @Test
    void testCreateInvalidCoach() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->  new Coach("   ", "Николай", "Сергеевич"));
        Assertions.assertThrows(IllegalArgumentException.class, () ->  new Coach("Иванов", null, "Сергеевич"));
    }

    @Test
    void testCreateInvalidGroup() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->  new Group("   ", Age.CHILD, 1));
        Assertions.assertThrows(IllegalArgumentException.class, () ->  new Group("Акробатика", Age.CHILD, -1));
        Assertions.assertThrows(NullPointerException.class, () ->  new Group("Акробатика", null, 1));
    }

    @Test
    void testCreateInvalidTimeOfDay() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->  new TimeOfDay(-1, 0));
        Assertions.assertThrows(IllegalArgumentException.class, () ->  new TimeOfDay(24, 0));
        Assertions.assertThrows(IllegalArgumentException.class, () ->  new TimeOfDay(13, -1));
        Assertions.assertThrows(IllegalArgumentException.class, () ->  new TimeOfDay(13, 60));
    }

    @Test
    void testGetCountByCoachesSingleCoach() {
        Timetable timetable = new Timetable();
        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        TrainingSession firstTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession secondTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0));
        TrainingSession thirdTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);
        timetable.addNewTrainingSession(thirdTrainingSession);

        Map<Coach, Long> counts = timetable.getCountByCoaches();
        Assertions.assertEquals(1, counts.size());
        Assertions.assertEquals(3, counts.get(coach));
    }

    @Test
    void testGetCountByCoachesWithoutSessions() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Map<Coach, Long> counts = timetable.getCountByCoaches();
        Assertions.assertEquals(0, counts.size());
        Assertions.assertNull(counts.get(coach));
    }

    @Test
    void testGetCountByCoachesMultipleCoach() {
        Timetable timetable = new Timetable();
        Group firstGroup = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach firstCoach = new Coach("Васильев", "Николай", "Сергеевич");

        TrainingSession firstTrainingSession = new TrainingSession(firstGroup, firstCoach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession secondTrainingSession = new TrainingSession(firstGroup, firstCoach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0));
        TrainingSession thirdTrainingSession = new TrainingSession(firstGroup, firstCoach,
                DayOfWeek.FRIDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);
        timetable.addNewTrainingSession(thirdTrainingSession);

        Group secondGroup = new Group("Акробатика для взрослых", Age.ADULT, 60);
        Coach secondCoach = new Coach("Иванов", "Иван", "Иванович");

        timetable.addNewTrainingSession(new TrainingSession(secondGroup, secondCoach,
                DayOfWeek.TUESDAY, new TimeOfDay(13, 0)));
        timetable.addNewTrainingSession(new TrainingSession(secondGroup, secondCoach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0)));
        timetable.addNewTrainingSession(new TrainingSession(secondGroup, secondCoach,
                DayOfWeek.FRIDAY, new TimeOfDay(15, 0)));
        timetable.addNewTrainingSession(new TrainingSession(secondGroup, secondCoach,
                DayOfWeek.SUNDAY, new TimeOfDay(15, 0)));

        Map<Coach, Long> counts = timetable.getCountByCoaches();
        Assertions.assertEquals(2, counts.size());
        Assertions.assertEquals(3, counts.get(firstCoach));
        Assertions.assertEquals(4, counts.get(secondCoach));

        List<Coach> sortedCoaches = new ArrayList<>(counts.keySet());
        Assertions.assertEquals(secondCoach, sortedCoaches.getFirst());
        Assertions.assertEquals(firstCoach, sortedCoaches.getLast());
    }

}
