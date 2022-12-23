package ru.soft.testdata.snapshot;

import ru.soft.common.data.snapshot.*;
import ru.soft.common.to.ExerciseTo;
import ru.soft.common.to.WorkoutPlanTo;
import ru.soft.common.to.WorkoutRoundTo;
import ru.soft.testdata.to.ExerciseToTestDataStore;
import ru.soft.testdata.to.WorkoutPlanToTestDataStore;
import ru.soft.testdata.to.WorkoutRoundTestDataStore;

import java.util.List;
import java.util.stream.Stream;

public final class TestSnapshotStore {

    private TestSnapshotStore() {
    }

    public static ExerciseSnapshot exerciseSnapshot() {
        ExerciseTo to = ExerciseToTestDataStore.TO_1;
        return new ExerciseSnapshot(
                to.title(),
                to.description(),
                to.complexity()
        );
    }

    public static WorkoutPlanSnapshot workoutPlanSnapshot() {
        WorkoutPlanTo to = WorkoutPlanToTestDataStore.TO_1;
        return new WorkoutPlanSnapshot(
                workoutSchemaSnapshot(),
                to.title(),
                to.description()
        );
    }

    public static WorkoutRoundSnapshot workoutRoundSnapshot() {
        WorkoutRoundTo to = WorkoutRoundTestDataStore.TO_1;
        return new WorkoutRoundSnapshot(
                workoutRoundSchemaSnapshot(),
                to.title(),
                to.description()
        );
    }

    public static WorkoutSchemaSnapshot workoutSchemaSnapshot() {
        List<WorkoutRoundSnapshot> workoutRoundSnapshots =
                Stream.of(WorkoutRoundTestDataStore.TO_1, WorkoutRoundTestDataStore.TO_2, WorkoutRoundTestDataStore.TO_3)
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
                Stream.of(ExerciseToTestDataStore.TO_1, ExerciseToTestDataStore.TO_2, ExerciseToTestDataStore.TO_3)
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
