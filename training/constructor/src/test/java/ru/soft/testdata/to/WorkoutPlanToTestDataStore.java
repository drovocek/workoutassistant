package ru.soft.testdata.to;

import org.springframework.stereotype.Component;
import ru.soft.common.to.WorkoutPlanTo;
import ru.soft.testdata.TestDataStore;

import java.util.List;
import java.util.UUID;

import static ru.soft.testdata.snapshot.TestSnapshotStore.workoutSchemaSnapshot;

@Component
public class WorkoutPlanToTestDataStore implements TestDataStore<WorkoutPlanTo> {

    public static final String DUPLICATE_TITLE = "Barbell squats training";

    @Override
    public WorkoutPlanTo entity(boolean isNew) {
        return new WorkoutPlanTo(
                isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"),
                workoutSchemaSnapshot()
                , "Push-up training",
                "Push-up description"
        );
    }

    @Override
    public List<WorkoutPlanTo> entities(boolean isNew) {
        return List.of(
                entity(isNew),
                new WorkoutPlanTo(
                        isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"),
                        workoutSchemaSnapshot()
                        , "Push-up training",
                        "Push-up description"
                ),
                new WorkoutPlanTo(
                        isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"),
                        workoutSchemaSnapshot()
                        , "Push-up training",
                        "Push-up description"
                )
        );
    }

    @Override
    public WorkoutPlanTo requestEntity(boolean isNew) {
        return new WorkoutPlanTo(
                isNew ? null : NEW_ID,
                workoutSchemaSnapshot(),
                "request title",
                "request description"
        );
    }

    @Override
    public List<WorkoutPlanTo> invalids(boolean isNew) {
        return List.of(
                new WorkoutPlanTo(
                        isNew ? null : newId(),
                        workoutSchemaSnapshot(),
                        "",
                        "request description"
                ),
                new WorkoutPlanTo(
                        isNew ? newId() : null,
                        workoutSchemaSnapshot(),
                        "request title",
                        "request description"
                ),
                new WorkoutPlanTo(
                        isNew ? null : newId(),
                        null,
                        "request title",
                        "request description"
                )
        );
    }

    @Override
    public List<WorkoutPlanTo> duplicates(boolean isNew) {
        return List.of(
                new WorkoutPlanTo(
                        isNew ? null : newId(),
                        workoutSchemaSnapshot(),
                        DUPLICATE_TITLE,
                        "request description"
                )
        );
    }

    @Override
    public List<WorkoutPlanTo> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
