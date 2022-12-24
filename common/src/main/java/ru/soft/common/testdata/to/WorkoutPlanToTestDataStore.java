package ru.soft.common.testdata.to;

import org.springframework.stereotype.Component;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.to.WorkoutPlanTo;

import java.util.List;
import java.util.UUID;

import static ru.soft.common.testdata.snapshot.TestSnapshotStore.workoutSchemaSnapshot;

@Component
public class WorkoutPlanToTestDataStore implements TestDataStore<WorkoutPlanTo> {

    public static final String DUPLICATE_TITLE = "Barbell squats plan title";

    public static WorkoutPlanTo example(boolean isNew) {
        return new WorkoutPlanTo(
                isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"),
                workoutSchemaSnapshot()
                , "Push-up plan title",
                "Push-up plan description"
        );
    }

    @Override
    public WorkoutPlanTo entity(boolean isNew) {
        return example(isNew);
    }

    @Override
    public List<WorkoutPlanTo> entities(boolean isNew) {
        return List.of(
                entity(isNew),
                new WorkoutPlanTo(
                        isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"),
                        workoutSchemaSnapshot()
                        , "Barbell squats plan title",
                        "Barbell squats plan description"
                ),
                new WorkoutPlanTo(
                        isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"),
                        workoutSchemaSnapshot(),
                        "Pull-up plan title",
                        "Pull-up plan description"
                )
        );
    }

    @Override
    public WorkoutPlanTo requestEntity(boolean isNew) {
        return new WorkoutPlanTo(
                isNew ? null : newId(),
                workoutSchemaSnapshot(),
                "request plan title",
                "request plan description"
        );
    }

    @Override
    public List<WorkoutPlanTo> invalids(boolean isNew) {
        return List.of(
                new WorkoutPlanTo(
                        isNew ? null : newId(),
                        workoutSchemaSnapshot(),
                        "",
                        "request plan description"
                ),
                new WorkoutPlanTo(
                        isNew ? newId() : null,
                        workoutSchemaSnapshot(),
                        "request plan title",
                        "request plan description"
                ),
                new WorkoutPlanTo(
                        isNew ? null : newId(),
                        null,
                        "request plan title",
                        "request plan description"
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
                        "request plan description"
                )
        );
    }

    @Override
    public List<WorkoutPlanTo> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
