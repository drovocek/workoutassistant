package ru.soft.data.model.utils;

import lombok.experimental.UtilityClass;
import ru.soft.data.model.snapshot.WorkoutRoundSchemaSnapshot;
import ru.soft.data.model.snapshot.WorkoutSchemaSnapshot;

@UtilityClass
public class ComplexityUtils {

    public static int calculateComplexity(WorkoutSchemaSnapshot workoutSchemaSnapshot) {
        return (int) workoutSchemaSnapshot.workoutRoundSnapshots().stream()
                .flatMap(w -> w.workoutRoundSchemaSnapshot().workoutStationSnapshots().stream())
                .mapToInt(s -> s.exerciseSnapshot().complexity())
                .average().orElse(0);
    }

    public static int calculateComplexity(WorkoutRoundSchemaSnapshot workoutRoundSchemaSnapshot) {
        return (int) workoutRoundSchemaSnapshot.workoutStationSnapshots().stream()
                .mapToInt(w -> w.exerciseSnapshot().complexity())
                .average().orElse(0);
    }

//    public static int calculateComplexity(List<WorkoutStationSnapshot> workoutStationSnapshots) {
//        return (int) workoutStationSnapshots.stream()
//                .mapToInt(station -> station.exerciseSnapshot().complexity())
//                .average().orElse(0);
//    }
}
