package ru.soft.testdata.model;

import org.springframework.stereotype.Component;
import ru.soft.data.model.WorkoutPlan;
import ru.soft.common.testdata.TestDataStore;

import java.util.List;
import java.util.UUID;

import static ru.soft.common.testdata.snapshot.TestSnapshotStore.workoutSchemaSnapshot;

@Component
public class WorkoutPlanTestDataStore implements TestDataStore<WorkoutPlan> {

    private static final String DUPLICATE_TITLE = "Barbell squats plan title";

    @Override
    public WorkoutPlan entity(boolean isNew) {
        return new WorkoutPlan(
                isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"),
                isNew,
                workoutSchemaSnapshot()
                , "Push-up plan title",
                "Push-up plan description"
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
                        , "Barbell squats plan title",
                        "Barbell squats plan description"
                ),
                new WorkoutPlan(
                        isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"),
                        isNew,
                        workoutSchemaSnapshot(),
                        "Pull-up plan title",
                        "Pull-up plan description"
                )
        );
    }

    @Override
    public WorkoutPlan requestEntity(boolean isNew) {
        return new WorkoutPlan(
                isNew ? null : newId(),
                isNew,
                workoutSchemaSnapshot(),
                "request plan title",
                "request plan description"
        );
    }

    @Override
    public List<WorkoutPlan> invalids(boolean isNew) {
        return List.of(
                new WorkoutPlan(
                        isNew ? null : newId(),
                        isNew,
                        workoutSchemaSnapshot(),
                        "",
                        "request plan description"
                ),
                new WorkoutPlan(
                        isNew ? newId() : null,
                        isNew,
                        workoutSchemaSnapshot(),
                        "request plan title",
                        "request plan description"
                ),
                new WorkoutPlan(
                        isNew ? null : newId(),
                        isNew,
                        null,
                        "request plan title",
                        "request plan description"
                )
        );
    }

    @Override
    public List<WorkoutPlan> duplicates(boolean isNew) {
        return List.of(
                new WorkoutPlan(
                        isNew ? null : newId(),
                        isNew,
                        workoutSchemaSnapshot(),
                        DUPLICATE_TITLE,
                        "request plan description"
                )
        );
    }

    @Override
    public List<WorkoutPlan> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
