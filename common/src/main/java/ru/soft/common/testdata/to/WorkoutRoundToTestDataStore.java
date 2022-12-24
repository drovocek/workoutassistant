package ru.soft.common.testdata.to;

import org.springframework.stereotype.Component;
import ru.soft.common.to.WorkoutRoundTo;
import ru.soft.common.testdata.TestDataStore;

import java.util.List;
import java.util.UUID;

import static ru.soft.common.testdata.snapshot.TestSnapshotStore.workoutRoundSchemaSnapshot;

@Component
public class WorkoutRoundToTestDataStore implements TestDataStore<WorkoutRoundTo> {

    private static final String DUPLICATE_TITLE = "Strength round title";

    public static WorkoutRoundTo example(boolean isNew) {
        return new WorkoutRoundTo(
                isNew ? null : UUID.fromString("3cd77b78-7a52-11ed-a1eb-0242ac120002"),
                workoutRoundSchemaSnapshot()
                , "Warm up round title",
                "Warm up round description"
        );
    }

    public static List<WorkoutRoundTo> examples(boolean isNew) {
        return List.of(
                example(isNew),
                new WorkoutRoundTo(
                        isNew ? null : UUID.fromString("425da3e2-7a52-11ed-a1eb-0242ac120002"),
                        workoutRoundSchemaSnapshot()
                        , "Strength round title",
                        "Strength round description"
                ),
                new WorkoutRoundTo(
                        isNew ? null : UUID.fromString("475617e4-7a52-11ed-a1eb-0242ac120002"),
                        workoutRoundSchemaSnapshot()
                        , "Endurance round title",
                        "Endurance round description"
                )
        );
    }

    @Override
    public WorkoutRoundTo entity(boolean isNew) {
        return example(isNew);
    }

    @Override
    public List<WorkoutRoundTo> entities(boolean isNew) {
        return examples(isNew);
    }

    @Override
    public WorkoutRoundTo requestEntity(boolean isNew) {
        return new WorkoutRoundTo(
                isNew ? null : newId(),
                workoutRoundSchemaSnapshot(),
                "request round title",
                "request round description"
        );
    }

    @Override
    public List<WorkoutRoundTo> invalids(boolean isNew) {
        return List.of(
                new WorkoutRoundTo(
                        isNew ? null : newId(),
                        workoutRoundSchemaSnapshot(),
                        "",
                        "request round description"
                ),
                new WorkoutRoundTo(
                        isNew ? newId() : null,
                        workoutRoundSchemaSnapshot(),
                        "request round title",
                        "request round description"
                ),
                new WorkoutRoundTo(
                        isNew ? null : newId(),
                        null,
                        "request round title",
                        "request round description"
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
                        "request round description"
                )
        );
    }

    @Override
    public List<WorkoutRoundTo> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
