package ru.soft.testdata.model;

import org.springframework.stereotype.Component;
import ru.soft.data.model.WorkoutRound;
import ru.soft.common.testdata.TestDataStore;

import java.util.List;
import java.util.UUID;

import static ru.soft.common.testdata.snapshot.TestSnapshotStore.workoutRoundSchemaSnapshot;

@Component
public class WorkoutRoundTestDataStore implements TestDataStore<WorkoutRound> {

    private static final String DUPLICATE_TITLE = "Strength round title";

    @Override
    public WorkoutRound entity(boolean isNew) {
        return new WorkoutRound(
                isNew ? null : UUID.fromString("3cd77b78-7a52-11ed-a1eb-0242ac120002"),
                isNew,
                workoutRoundSchemaSnapshot()
                , "Warm up round title",
                "Warm up round description"
        );
    }

    @Override
    public List<WorkoutRound> entities(boolean isNew) {
        return List.of(
                entity(isNew),
                new WorkoutRound(
                        isNew ? null : UUID.fromString("425da3e2-7a52-11ed-a1eb-0242ac120002"),
                        isNew,
                        workoutRoundSchemaSnapshot()
                        , "Strength round title",
                        "Strength round description"
                ),
                new WorkoutRound(
                        isNew ? null : UUID.fromString("475617e4-7a52-11ed-a1eb-0242ac120002"),
                        isNew,
                        workoutRoundSchemaSnapshot()
                        , "Endurance round title",
                        "Endurance round description"
                )
        );
    }

    @Override
    public WorkoutRound requestEntity(boolean isNew) {
        return new WorkoutRound(
                isNew ? null : newId(),
                isNew,
                workoutRoundSchemaSnapshot(),
                "request round title",
                "request round description"
        );
    }

    @Override
    public List<WorkoutRound> invalids(boolean isNew) {
        return List.of(
                new WorkoutRound(
                        isNew ? null : newId(),
                        isNew,
                        workoutRoundSchemaSnapshot(),
                        "",
                        "request round description"
                ),
                new WorkoutRound(
                        isNew ? newId() : null,
                        isNew,
                        workoutRoundSchemaSnapshot(),
                        "request round title",
                        "request round description"
                ),
                new WorkoutRound(
                        isNew ? null : newId(),
                        isNew,
                        null,
                        "request round title",
                        "request round description"
                )
        );
    }

    @Override
    public List<WorkoutRound> duplicates(boolean isNew) {
        return List.of(
                new WorkoutRound(
                        isNew ? null : newId(),
                        isNew,
                        workoutRoundSchemaSnapshot(),
                        DUPLICATE_TITLE,
                        "request round description"
                )
        );
    }

    @Override
    public List<WorkoutRound> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
