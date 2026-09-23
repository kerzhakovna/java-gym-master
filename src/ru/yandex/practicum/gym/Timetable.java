package ru.yandex.practicum.gym;

import java.util.*;
import java.util.stream.Collectors;

public class Timetable {

    private final Map<DayOfWeek, Map<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        if (trainingSession == null) {
            throw new NullPointerException("Training session must not be null");
        }

        timetable.computeIfAbsent(trainingSession.getDayOfWeek(), dayOfWeek -> new TreeMap<>())
                .computeIfAbsent(trainingSession.getTimeOfDay(), timeOfDay -> new ArrayList<>())
                .add(trainingSession);
    }

    public Map<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.getOrDefault(dayOfWeek, Map.of());
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        return timetable.getOrDefault(dayOfWeek, Map.of())
                .getOrDefault(timeOfDay, List.of());
    }

    public Map<Coach, Long> getCountByCoaches() {
        Map<Coach, Long> groupingCoaches = timetable.values()
                .stream()
                .map(Map::values)
                .flatMap(Collection::stream)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(
                        TrainingSession::getCoach,
                        Collectors.counting()
                ));

        return groupingCoaches.entrySet()
                .stream()
                .sorted(Map.Entry.<Coach, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

}
