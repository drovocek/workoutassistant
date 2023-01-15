package ru.soft.common.testdata.snapshot;

import ru.soft.common.data.RoundsSchema;
import ru.soft.common.data.Station;
import ru.soft.common.data.StationsSchema;
import ru.soft.common.data.snapshot.ExerciseSnapshot;
import ru.soft.common.data.snapshot.RoundSnapshot;
import ru.soft.common.data.snapshot.WorkoutSnapshot;
import ru.soft.common.testdata.to.ExerciseToTestDataStore;
import ru.soft.common.testdata.to.WorkoutPlanToTestDataStore;
import ru.soft.common.testdata.to.WorkoutRoundToTestDataStore;
import ru.soft.common.to.ExerciseTo;
import ru.soft.common.to.RoundTo;
import ru.soft.common.to.WorkoutTo;

import java.util.List;

public final class TestSnapshotStore {

    private TestSnapshotStore() {
    }

    public static ExerciseSnapshot exerciseSnapshot() {
        ExerciseTo to = ExerciseToTestDataStore.example(true);
        return ExerciseSnapshot.builder()
                .title(to.title())
                .description(to.description())
                .complexity(to.complexity())
                .build();
    }

    public static WorkoutSnapshot planSnapshot() {
        WorkoutTo to = WorkoutPlanToTestDataStore.example(true);
        return WorkoutSnapshot.builder()
                .roundsSchema(roundsSchema())
                .title(to.title())
                .description(to.description())
                .build();
    }

    public static RoundSnapshot roundSnapshot() {
        RoundTo to = WorkoutRoundToTestDataStore.example(true);
        return RoundSnapshot.builder()
                .stationsSchema(stationsSchema())
                .title(to.title())
                .description(to.description())
                .build();
    }

    public static RoundsSchema roundsSchema() {
        List<RoundSnapshot> roundSnapshots =
                WorkoutRoundToTestDataStore.examples(true).stream()
                        .map(round ->
                                RoundSnapshot.builder()
                                        .stationsSchema(stationsSchema())
                                        .title(round.title())
                                        .description(round.description())
                                        .build())
                        .toList();
        return new RoundsSchema(roundSnapshots);
    }

    public static StationsSchema stationsSchema() {
        List<ExerciseSnapshot> exerciseSnapshots =
                ExerciseToTestDataStore.examples(true).stream()
                        .map(exercise -> ExerciseSnapshot.builder()
                                .title(exercise.title())
                                .description(exercise.description())
                                .complexity(exercise.complexity())
                                .build())
                        .toList();
        List<Station> stations = exerciseSnapshots.stream()
                .map(exerciseSnapshot -> Station.builder()
                        .exerciseSnapshot(exerciseSnapshot)
                        .repetitions(3)
                        .weight(0)
                        .duration(100)
                        .rest(20)
                        .order(1)
                        .build())
                .toList();
        return new StationsSchema(stations);
    }
}
