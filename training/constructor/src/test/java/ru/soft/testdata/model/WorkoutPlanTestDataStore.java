package ru.soft.testdata.model;

import org.springframework.stereotype.Component;
import ru.soft.data.model.WorkoutPlan;
import ru.soft.testdata.TestDataStore;

import java.util.List;
import java.util.UUID;

import static ru.soft.testdata.snapshot.TestSnapshotStore.workoutSchemaSnapshot;

@Component
public class WorkoutPlanTestDataStore implements TestDataStore<WorkoutPlan> {

    private static final String DUPLICATE_TITLE = "Barbell squats training";

    @Override
    public WorkoutPlan entity(boolean isNew) {
        return new WorkoutPlan(
                isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"),
                isNew,
                workoutSchemaSnapshot()
                , "Push-up training",
                "Push-up description"
        );
    }

    @Override
    public List<WorkoutPlan> entities(boolean isNew) {
        return List.of(
                entity(isNew),
                new WorkoutPlan(
                        isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"),
                        isNew,
                        workoutSchemaSnapshot()
                        , "Push-up training",
                        "Push-up description"
                ),
                new WorkoutPlan(
                        isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"),
                        isNew,
                        workoutSchemaSnapshot()
                        , "Push-up training",
                        "Push-up description"
                )
        );
    }

    @Override
    public WorkoutPlan requestEntity(boolean isNew) {
        return new WorkoutPlan(
                isNew ? null : newId(),
                isNew,
                workoutSchemaSnapshot(),
                "request title",
                "request description"
        );
    }

    @Override
    public List<WorkoutPlan> invalids(boolean isNew) {
        return List.of(
                new WorkoutPlan(
                        isNew ? null : newId(),
                        workoutSchemaSnapshot(),
                        "",
                        "request description"
                ),
                new WorkoutPlan(
                        isNew ? newId() : null,
                        workoutSchemaSnapshot(),
                        "request title",
                        "request description"
                ),
                new WorkoutPlan(
                        isNew ? null : newId(),
                        null,
                        "request title",
                        "request description"
                )
        );
    }

    @Override
    public List<WorkoutPlan> duplicates(boolean isNew) {
        return List.of(
                new WorkoutPlan(
                        isNew ? null : newId(),
                        workoutSchemaSnapshot(),
                        DUPLICATE_TITLE,
                        "request description"
                )
        );
    }

    @Override
    public List<WorkoutPlan> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
