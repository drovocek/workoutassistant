package ru.soft.testdata.model;

import org.springframework.stereotype.Component;
import ru.soft.data.model.WorkoutRound;
import ru.soft.testdata.TestDataStore;

import java.util.List;
import java.util.UUID;

import static ru.soft.testdata.snapshot.TestSnapshotStore.workoutRoundSchemaSnapshot;

@Component
public class WorkoutRoundTestDataStore implements TestDataStore<WorkoutRound> {

    public static final WorkoutRound TO_1 =
            new WorkoutRound(
                    UUID.fromString("3cd77b78-7a52-11ed-a1eb-0242ac120002"),
                    false,
                    workoutRoundSchemaSnapshot()
                    , "Warm up",
                    "Warm up"
            );

    public static final WorkoutRound TO_2 =
            new WorkoutRound(
                    UUID.fromString("425da3e2-7a52-11ed-a1eb-0242ac120002"),
                    false,
                    workoutRoundSchemaSnapshot()
                    , "Strength",
                    "For strength"
            );

    public static final WorkoutRound TO_3 =
            new WorkoutRound(
                    UUID.fromString("475617e4-7a52-11ed-a1eb-0242ac120002"),
                    false,
                    workoutRoundSchemaSnapshot()
                    , "Endurance",
                    "For endurance"
            );

    private static final String DUPLICATE_TITLE = "Strength";

    @Override
    public WorkoutRound entity() {
        return TO_1;
    }

    @Override
    public List<WorkoutRound> entities() {
        return List.of(TO_1, TO_2, TO_3);
    }

    @Override
    public WorkoutRound requestEntity(boolean isNew) {
        return new WorkoutRound(
                isNew ? null : TO_1.id(),
                isNew,
                workoutRoundSchemaSnapshot(),
                "request title",
                "request description"
        );
    }

    @Override
    public List<WorkoutRound> invalids(boolean isNew) {
        return List.of(
                new WorkoutRound(
                        isNew ? null : TO_1.id(),
                        workoutRoundSchemaSnapshot(),
                        "",
                        "request description"
                ),
                new WorkoutRound(
                        isNew ? TO_1.id() : null,
                        workoutRoundSchemaSnapshot(),
                        "request title",
                        "request description"
                ),
                new WorkoutRound(
                        isNew ? null : TO_1.id(),
                        null,
                        "request title",
                        "request description"
                )
        );
    }

    @Override
    public List<WorkoutRound> duplicates(boolean isNew) {
        return List.of(
                new WorkoutRound(
                        isNew ? null : TO_1.id(),
                        workoutRoundSchemaSnapshot(),
                        DUPLICATE_TITLE,
                        "request description"
                )
        );
    }

    @Override
    public List<WorkoutRound> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
