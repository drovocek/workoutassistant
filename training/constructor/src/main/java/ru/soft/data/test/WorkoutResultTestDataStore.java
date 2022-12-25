package ru.soft.data.test;

import org.springframework.stereotype.Component;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.data.model.WorkoutResult;

import java.util.List;
import java.util.UUID;

import static ru.soft.common.testdata.snapshot.TestSnapshotStore.workoutSchemaSnapshot;

@Component
public class WorkoutResultTestDataStore implements TestDataStore<WorkoutResult> {

    private static final UUID SESSION_ID = UUID.fromString("ae9b7996-7ac8-11ed-a1eb-0242ac120002");
    private static final String DUPLICATE_TITLE = "Barbell squat result title";

    @Override
    public WorkoutResult entity(boolean isNew) {
        return entity(isNew, UUID.fromString("a34798c2-7ac8-11ed-a1eb-0242ac120002"));
    }

    public WorkoutResult entity(boolean isNew, UUID sessionId) {
        return new WorkoutResult(
                isNew ? null : UUID.fromString("6ab39d60-7a52-11ed-a1eb-0242ac120002"),
                sessionId,
                isNew,
                workoutSchemaSnapshot()
                , "Push-up result title",
                "Push-up result description"
        );
    }

    @Override
    public List<WorkoutResult> entities(boolean isNew) {
        return entities(isNew,
                UUID.fromString("a34798c2-7ac8-11ed-a1eb-0242ac120002"),
                UUID.fromString("a9323cf6-7ac8-11ed-a1eb-0242ac120002"));
    }

    public List<WorkoutResult> entities(boolean isNew, UUID... sessionIds) {
        return List.of(
                entity(isNew, sessionIds[0]),
                new WorkoutResult(
                        isNew ? null : UUID.fromString("6f8f8c22-7a52-11ed-a1eb-0242ac120002"),
                        sessionIds[1],
                        isNew,
                        workoutSchemaSnapshot()
                        , "Barbell squat result title",
                        "Barbell squat result description"
                )
        );
    }

    @Override
    public WorkoutResult requestEntity(boolean isNew) {
        return requestEntity(isNew, SESSION_ID);
    }

    public WorkoutResult requestEntity(boolean isNew, UUID sessionId) {
        return new WorkoutResult(
                isNew ? null : newId(),
                sessionId,
                isNew,
                workoutSchemaSnapshot(),
                "request result title",
                "request result description"
        );
    }

    @Override
    public List<WorkoutResult> invalids(boolean isNew) {
        return invalids(isNew, SESSION_ID);
    }

    public List<WorkoutResult> invalids(boolean isNew, UUID sessionId) {
        return List.of(
                new WorkoutResult(
                        isNew ? null : newId(),
                        sessionId,
                        isNew,
                        workoutSchemaSnapshot(),
                        "",
                        "request result description"
                ),
                new WorkoutResult(
                        isNew ? newId() : null,
                        sessionId,
                        isNew,
                        workoutSchemaSnapshot(),
                        null,
                        "request result description"
                ),
                new WorkoutResult(
                        isNew ? newId() : null,
                        null,
                        isNew,
                        workoutSchemaSnapshot(),
                        "request result title",
                        "request result description"
                ),
                new WorkoutResult(
                        isNew ? null : newId(),
                        sessionId,
                        isNew,
                        null,
                        "request result title",
                        "request result description"
                )
        );
    }

    @Override
    public List<WorkoutResult> duplicates(boolean isNew) {
        return duplicates(isNew, SESSION_ID);
    }

    public List<WorkoutResult> duplicates(boolean isNew, UUID sessionID) {
        return List.of(
                new WorkoutResult(
                        isNew ? null : newId(),
                        sessionID,
                        isNew,
                        workoutSchemaSnapshot(),
                        DUPLICATE_TITLE,
                        "request result description"
                )
        );
    }

    @Override
    public List<WorkoutResult> htmlUnsafe(boolean isNew) {
        return htmlUnsafe(isNew, SESSION_ID);
    }

    public List<WorkoutResult> htmlUnsafe(boolean isNew, UUID sessionId) {
        return List.of();
    }
}
