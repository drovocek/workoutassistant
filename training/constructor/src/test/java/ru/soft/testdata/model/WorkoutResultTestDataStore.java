package ru.soft.testdata.model;

import org.springframework.stereotype.Component;
import ru.soft.data.model.WorkoutResult;
import ru.soft.testdata.TestDataStore;

import java.util.List;
import java.util.UUID;

import static ru.soft.testdata.snapshot.TestSnapshotStore.workoutSchemaSnapshot;

@Component
public class WorkoutResultTestDataStore implements TestDataStore<WorkoutResult> {

    private static final UUID EXITED_SESSION_ID = UUID.fromString("ae9b7996-7ac8-11ed-a1eb-0242ac120002");
    private static final String DUPLICATE_TITLE = "Barbell squats training";

    @Override
    public WorkoutResult entity(boolean isNew) {
        return new WorkoutResult(
                isNew ? null : UUID.fromString("6ab39d60-7a52-11ed-a1eb-0242ac120002"),
                UUID.fromString("a34798c2-7ac8-11ed-a1eb-0242ac120002"),
                false,
                workoutSchemaSnapshot()
                , "Push-up training",
                "Push-up description"
        );
    }

    @Override
    public List<WorkoutResult> entities(boolean isNew) {
        return List.of(
                entity(isNew),
                new WorkoutResult(
                        UUID.fromString("6f8f8c22-7a52-11ed-a1eb-0242ac120002"),
                        UUID.fromString("a9323cf6-7ac8-11ed-a1eb-0242ac120002"),
                        false,
                        workoutSchemaSnapshot()
                        , "Barbell squat title",
                        "Barbell squat description"
                )
        );
    }

    @Override
    public WorkoutResult requestEntity(boolean isNew) {
        return new WorkoutResult(
                isNew ? null : newId(),
                EXITED_SESSION_ID,
                isNew,
                workoutSchemaSnapshot(),
                "request title",
                "request description"
        );
    }

    @Override
    public List<WorkoutResult> invalids(boolean isNew) {
        return List.of(
                new WorkoutResult(
                        isNew ? null : newId(),
                        EXITED_SESSION_ID,
                        workoutSchemaSnapshot(),
                        "",
                        "request description"
                ),
                new WorkoutResult(
                        isNew ? newId() : null,
                        EXITED_SESSION_ID,
                        workoutSchemaSnapshot(),
                        null,
                        "request description"
                ),
                new WorkoutResult(
                        isNew ? newId() : null,
                        null,
                        workoutSchemaSnapshot(),
                        "request title",
                        "request description"
                ),
                new WorkoutResult(
                        isNew ? null : newId(),
                        EXITED_SESSION_ID,
                        null,
                        "request title",
                        "request description"
                )
        );
    }

    @Override
    public List<WorkoutResult> duplicates(boolean isNew) {
        return List.of(
                new WorkoutResult(
                        isNew ? null : newId(),
                        EXITED_SESSION_ID,
                        workoutSchemaSnapshot(),
                        DUPLICATE_TITLE,
                        "request description"
                )
        );
    }

    @Override
    public List<WorkoutResult> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
