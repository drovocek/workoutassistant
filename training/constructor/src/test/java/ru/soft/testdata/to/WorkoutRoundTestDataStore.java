package ru.soft.testdata.to;

import org.springframework.stereotype.Component;
import ru.soft.common.to.WorkoutRoundTo;
import ru.soft.testdata.TestDataStore;

import java.util.List;
import java.util.UUID;

import static ru.soft.testdata.snapshot.TestSnapshotStore.workoutRoundSchemaSnapshot;

@Component
public class WorkoutRoundTestDataStore implements TestDataStore<WorkoutRoundTo> {

    private static final String DUPLICATE_TITLE = "Strength";

    @Override
    public WorkoutRoundTo entity(boolean isNew) {
        return new WorkoutRoundTo(
                UUID.fromString("3cd77b78-7a52-11ed-a1eb-0242ac120002"),
                workoutRoundSchemaSnapshot()
                , "Warm up",
                "Warm up"
        );
    }

    @Override
    public List<WorkoutRoundTo> entities(boolean isNew) {
        return List.of(
                entity(isNew),
                new WorkoutRoundTo(
                        UUID.fromString("425da3e2-7a52-11ed-a1eb-0242ac120002"),
                        workoutRoundSchemaSnapshot()
                        , "Strength",
                        "For strength"
                ),
                new WorkoutRoundTo(
                        UUID.fromString("475617e4-7a52-11ed-a1eb-0242ac120002"),
                        workoutRoundSchemaSnapshot()
                        , "Endurance",
                        "For endurance"
                )
        );
    }

    @Override
    public WorkoutRoundTo requestEntity(boolean isNew) {
        return new WorkoutRoundTo(
                isNew ? null : newId(),
                workoutRoundSchemaSnapshot(),
                "request title",
                "request description"
        );
    }

    @Override
    public List<WorkoutRoundTo> invalids(boolean isNew) {
        return List.of(
                new WorkoutRoundTo(
                        isNew ? null : newId(),
                        workoutRoundSchemaSnapshot(),
                        "",
                        "request description"
                ),
                new WorkoutRoundTo(
                        isNew ? newId() : null,
                        workoutRoundSchemaSnapshot(),
                        "request title",
                        "request description"
                ),
                new WorkoutRoundTo(
                        isNew ? null : newId(),
                        null,
                        "request title",
                        "request description"
                )
        );
    }

    @Override
    public List<WorkoutRoundTo> duplicates(boolean isNew) {
        return List.of(
                new WorkoutRoundTo(
                        isNew ? null : newId(),
                        workoutRoundSchemaSnapshot(),
                        DUPLICATE_TITLE,
                        "request description"
                )
        );
    }

    @Override
    public List<WorkoutRoundTo> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
