package ru.soft.testdata.model;

import org.springframework.stereotype.Component;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.data.model.WorkoutSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static ru.soft.common.testdata.snapshot.TestSnapshotStore.workoutPlanSnapshot;

@Component
public class WorkoutSessionTestDataStore implements TestDataStore<WorkoutSession> {

    private static final LocalDateTime SESSION_DATE_TIME = LocalDateTime.of(2021, 12, 1, 1, 2);

    @Override
    public WorkoutSession entity(boolean isNew) {
        return new WorkoutSession(
                isNew ? null : UUID.fromString("a34798c2-7ac8-11ed-a1eb-0242ac120002"),
                isNew,
                workoutPlanSnapshot(),
                LocalDateTime.of(2024, 3, 20, 19, 10, 25),
                "Easy session note"
        );
    }

    @Override
    public List<WorkoutSession> entities(boolean isNew) {
        return List.of(
                entity(isNew),
                new WorkoutSession(
                        isNew ? null : UUID.fromString("a9323cf6-7ac8-11ed-a1eb-0242ac120002"),
                        isNew,
                        workoutPlanSnapshot(),
                        LocalDateTime.of(2024, 3, 20, 19, 10, 25),
                        "Medium session note"
                ),
                new WorkoutSession(
                        isNew ? null : UUID.fromString("ae9b7996-7ac8-11ed-a1eb-0242ac120002"),
                        isNew,
                        workoutPlanSnapshot(),
                        LocalDateTime.of(2024, 3, 20, 19, 10, 25),
                        "Hard session note"
                )
        );
    }

    @Override
    public WorkoutSession requestEntity(boolean isNew) {
        return new WorkoutSession(
                isNew ? null : newId(),
                isNew,
                workoutPlanSnapshot(),
                SESSION_DATE_TIME,
                "request session note"
        );
    }

    @Override
    public List<WorkoutSession> invalids(boolean isNew) {
        return List.of(
                new WorkoutSession(
                        isNew ? null : newId(),
                        isNew,
                        workoutPlanSnapshot(),
                        null,
                        "request session note"
                ),
                new WorkoutSession(
                        isNew ? null : newId(),
                        isNew,
                        null,
                        SESSION_DATE_TIME,
                        "request session note"
                )
        );
    }

    @Override
    public List<WorkoutSession> duplicates(boolean isNew) {
        return List.of();
    }

    @Override
    public List<WorkoutSession> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
