package ru.soft.common.testdata.to;

import org.springframework.stereotype.Component;
import ru.soft.common.to.WorkoutSessionTo;
import ru.soft.common.testdata.TestDataStore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static ru.soft.common.testdata.snapshot.TestSnapshotStore.planSnapshot;

@Component
public class WorkoutSessionToTestDataStore implements TestDataStore<WorkoutSessionTo> {

    private static final LocalDateTime SESSION_DATE_TIME = LocalDateTime.of(2021, 12, 1, 1, 2);

    public static WorkoutSessionTo example(boolean isNew) {
        return new WorkoutSessionTo(
                isNew ? null : UUID.fromString("a34798c2-7ac8-11ed-a1eb-0242ac120002"),
                planSnapshot(),
                LocalDateTime.of(2024, 3, 20, 19, 10, 25),
                "Easy session note"
        );
    }

    @Override
    public WorkoutSessionTo entity(boolean isNew) {
        return example(isNew);
    }

    @Override
    public List<WorkoutSessionTo> entities(boolean isNew) {
        return List.of(
                entity(isNew),
                new WorkoutSessionTo(
                        isNew ? null : UUID.fromString("a9323cf6-7ac8-11ed-a1eb-0242ac120002"),
                        planSnapshot(),
                        LocalDateTime.of(2024, 3, 20, 19, 10, 25),
                        "Medium session note"
                ),
                new WorkoutSessionTo(
                        isNew ? null : UUID.fromString("ae9b7996-7ac8-11ed-a1eb-0242ac120002"),
                        planSnapshot(),
                        LocalDateTime.of(2024, 3, 20, 19, 10, 25),
                        "Hard session note"
                )
        );
    }

    @Override
    public WorkoutSessionTo requestEntity(boolean isNew) {
        return new WorkoutSessionTo(
                isNew ? null : newId(),
                planSnapshot(),
                SESSION_DATE_TIME,
                "request session note"
        );
    }

    @Override
    public List<WorkoutSessionTo> invalids(boolean isNew) {
        return List.of(
                new WorkoutSessionTo(
                        isNew ? null : newId(),
                        planSnapshot(),
                        null,
                        "request session note"
                ),
                new WorkoutSessionTo(
                        isNew ? null : newId(),
                        null,
                        SESSION_DATE_TIME,
                        "request session note"
                )
        );
    }

    @Override
    public List<WorkoutSessionTo> duplicates(boolean isNew) {
        return List.of();
    }

    @Override
    public List<WorkoutSessionTo> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
