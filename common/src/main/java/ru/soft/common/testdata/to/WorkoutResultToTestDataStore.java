package ru.soft.common.testdata.to;

import org.springframework.stereotype.Component;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.to.WorkoutResultTo;

import java.util.List;
import java.util.UUID;

import static ru.soft.common.testdata.snapshot.TestSnapshotStore.workoutSchemaSnapshot;

@Component
public class WorkoutResultToTestDataStore implements TestDataStore<WorkoutResultTo> {

    private static final UUID SESSION_ID = UUID.fromString("ae9b7996-7ac8-11ed-a1eb-0242ac120002");
    private static final String DUPLICATE_TITLE = "Barbell squat result title";

    public static WorkoutResultTo example(boolean isNew) {
        return example(isNew, UUID.fromString("a34798c2-7ac8-11ed-a1eb-0242ac120002"));
    }

    public static WorkoutResultTo example(boolean isNew, UUID sessionId) {
        return new WorkoutResultTo(
                isNew ? null : UUID.fromString("6ab39d60-7a52-11ed-a1eb-0242ac120002"),
                sessionId,
                workoutSchemaSnapshot()
                , "Push-up result title",
                "Push-up result description"
        );
    }

    @Override
    public WorkoutResultTo entity(boolean isNew) {
        return example(isNew);
    }

    public WorkoutResultTo entity(boolean isNew, UUID sessionId) {
        return example(isNew, sessionId);
    }

    @Override
    public List<WorkoutResultTo> entities(boolean isNew) {
        return entities(isNew, UUID.fromString("a34798c2-7ac8-11ed-a1eb-0242ac120002"), UUID.fromString("a9323cf6-7ac8-11ed-a1eb-0242ac120002"));
    }

    public List<WorkoutResultTo> entities(boolean isNew, UUID... sessionIds) {
        return List.of(
                entity(isNew, sessionIds[0]),
                new WorkoutResultTo(
                        isNew ? null : UUID.fromString("6f8f8c22-7a52-11ed-a1eb-0242ac120002"),
                        sessionIds[1],
                        workoutSchemaSnapshot()
                        , "Barbell squat result title",
                        "Barbell squat result description"
                )
        );
    }

    @Override
    public WorkoutResultTo requestEntity(boolean isNew) {
        return requestEntity(isNew, SESSION_ID);
    }

    public WorkoutResultTo requestEntity(boolean isNew, UUID sessionId) {
        return new WorkoutResultTo(
                isNew ? null : newId(),
                sessionId,
                workoutSchemaSnapshot(),
                "request result title",
                "request result description"
        );
    }

    @Override
    public List<WorkoutResultTo> invalids(boolean isNew) {
        return invalids(isNew, SESSION_ID);
    }

    public List<WorkoutResultTo> invalids(boolean isNew, UUID sessionId) {
        return List.of(
                new WorkoutResultTo(
                        isNew ? null : newId(),
                        sessionId,
                        workoutSchemaSnapshot(),
                        "",
                        "request result description"
                ),
                new WorkoutResultTo(
                        isNew ? newId() : null,
                        sessionId,
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
                        sessionId,
                        null,
                        "request result title",
                        "request result description"
                )
        );
    }

    @Override
    public List<WorkoutResultTo> duplicates(boolean isNew) {
        return duplicates(isNew, SESSION_ID);
    }

    public List<WorkoutResultTo> duplicates(boolean isNew, UUID sessionId) {
        return List.of(
                new WorkoutResultTo(
                        isNew ? null : newId(),
                        sessionId,
                        workoutSchemaSnapshot(),
                        DUPLICATE_TITLE,
                        "request result description"
                )
        );
    }

    @Override
    public List<WorkoutResultTo> htmlUnsafe(boolean isNew) {
        return htmlUnsafe(isNew, SESSION_ID);
    }

    public List<WorkoutResultTo> htmlUnsafe(boolean isNew, UUID sessionId) {
        return List.of();
    }
}
