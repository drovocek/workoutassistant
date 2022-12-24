package ru.soft.common.testdata.to;

import org.springframework.stereotype.Component;
import ru.soft.common.to.WorkoutResultTo;
import ru.soft.common.testdata.TestDataStore;

import java.util.List;
import java.util.UUID;

import static ru.soft.common.testdata.snapshot.TestSnapshotStore.workoutSchemaSnapshot;

@Component
public class WorkoutResultToTestDataStore implements TestDataStore<WorkoutResultTo> {

    private static final UUID EXITED_SESSION_ID = UUID.fromString("ae9b7996-7ac8-11ed-a1eb-0242ac120002");
    private static final String DUPLICATE_TITLE = "Barbell squats result title";

    public static WorkoutResultTo example(boolean isNew){
        return new WorkoutResultTo(
                isNew ? null : UUID.fromString("6ab39d60-7a52-11ed-a1eb-0242ac120002"),
                UUID.fromString("a34798c2-7ac8-11ed-a1eb-0242ac120002"),
                workoutSchemaSnapshot()
                , "Push-up result title",
                "Push-up result description"
        );
    }

    @Override
    public WorkoutResultTo entity(boolean isNew) {
        return example(isNew);
    }

    @Override
    public List<WorkoutResultTo> entities(boolean isNew) {
        return List.of(
                entity(isNew),
                new WorkoutResultTo(
                        isNew ? null : UUID.fromString("6f8f8c22-7a52-11ed-a1eb-0242ac120002"),
                        UUID.fromString("a9323cf6-7ac8-11ed-a1eb-0242ac120002"),
                        workoutSchemaSnapshot()
                        , "Barbell squat result title",
                        "Barbell squat result description"
                )
        );
    }

    @Override
    public WorkoutResultTo requestEntity(boolean isNew) {
        return new WorkoutResultTo(
                isNew ? null : newId(),
                EXITED_SESSION_ID,
                workoutSchemaSnapshot(),
                "request result title",
                "request result description"
        );
    }

    @Override
    public List<WorkoutResultTo> invalids(boolean isNew) {
        return List.of(
                new WorkoutResultTo(
                        isNew ? null : newId(),
                        EXITED_SESSION_ID,
                        workoutSchemaSnapshot(),
                        "",
                        "request result description"
                ),
                new WorkoutResultTo(
                        isNew ? newId() : null,
                        EXITED_SESSION_ID,
                        workoutSchemaSnapshot(),
                        null,
                        "request result description"
                ),
                new WorkoutResultTo(
                        isNew ? newId() : null,
                        null,
                        workoutSchemaSnapshot(),
                        "request result title",
                        "request result description"
                ),
                new WorkoutResultTo(
                        isNew ? null : newId(),
                        EXITED_SESSION_ID,
                        null,
                        "request result title",
                        "request result description"
                )
        );
    }

    @Override
    public List<WorkoutResultTo> duplicates(boolean isNew) {
        return List.of(
                new WorkoutResultTo(
                        isNew ? null : newId(),
                        EXITED_SESSION_ID,
                        workoutSchemaSnapshot(),
                        DUPLICATE_TITLE,
                        "request result description"
                )
        );
    }

    @Override
    public List<WorkoutResultTo> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
