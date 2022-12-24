package ru.soft.common.testdata.snapshot;

import ru.soft.common.data.snapshot.*;
import ru.soft.common.to.ExerciseTo;
import ru.soft.common.to.WorkoutPlanTo;
import ru.soft.common.to.WorkoutRoundTo;
import ru.soft.common.testdata.to.ExerciseToTestDataStore;
import ru.soft.common.testdata.to.WorkoutPlanToTestDataStore;
import ru.soft.common.testdata.to.WorkoutRoundToTestDataStore;

import java.util.List;

public final class TestSnapshotStore {

    private TestSnapshotStore() {
    }

    public static ExerciseSnapshot exerciseSnapshot() {
        ExerciseTo to = ExerciseToTestDataStore.example(true);
        return new ExerciseSnapshot(
                to.title(),
                to.description(),
                to.complexity()
        );
    }

    public static WorkoutPlanSnapshot workoutPlanSnapshot() {
        WorkoutPlanTo to = WorkoutPlanToTestDataStore.example(true);
        return new WorkoutPlanSnapshot(
                workoutSchemaSnapshot(),
                to.title(),
                to.description()
        );
    }

    public static WorkoutRoundSnapshot workoutRoundSnapshot() {
        WorkoutRoundTo to = WorkoutRoundToTestDataStore.example(true);
        return new WorkoutRoundSnapshot(
                workoutRoundSchemaSnapshot(),
                to.title(),
                to.description()
        );
    }

    public static WorkoutSchemaSnapshot workoutSchemaSnapshot() {
        List<WorkoutRoundSnapshot> workoutRoundSnapshots =
                WorkoutRoundToTestDataStore.examples(true).stream()
                        .map(round ->
                                WorkoutRoundSnapshot.builder()
                                        .workoutRoundSchemaSnapshot(workoutRoundSchemaSnapshot())
                                        .title(round.title())
                                        .description(round.description())
                                        .build())
                        .toList();
        return new WorkoutSchemaSnapshot(workoutRoundSnapshots);
    }

    public static WorkoutRoundSchemaSnapshot workoutRoundSchemaSnapshot() {
        List<ExerciseSnapshot> exerciseSnapshots =
                ExerciseToTestDataStore.examples(true).stream()
                        .map(exercise -> new ExerciseSnapshot(exercise.title(), exercise.description(), exercise.complexity()))
                        .toList();
        List<WorkoutStationSnapshot> workoutStationSnapshots = exerciseSnapshots.stream()
                .map(exerciseSnapshot ->
                        new WorkoutStationSnapshot(
                                exerciseSnapshot,
                                3, 0, 100, 20, 1)
                )
                .toList();
        return new WorkoutRoundSchemaSnapshot(workoutStationSnapshots);
    }
}
