package ru.soft.testdata.model;

import org.springframework.stereotype.Component;
import ru.soft.data.model.WorkoutSession;
import ru.soft.testdata.TestDataStore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static ru.soft.testdata.snapshot.TestSnapshotStore.workoutPlanSnapshot;

@Component
public class WorkoutSessionTestDataStore implements TestDataStore<WorkoutSession> {

    public static final WorkoutSession ENTITY_1 =
            new WorkoutSession(
                    UUID.fromString("f386d086-6e5d-11ed-a1eb-0242ac120002"),
                    false,
                    workoutPlanSnapshot(),
                    LocalDateTime.of(2016, 6, 22, 19, 10, 25),
                    "Easy training"
            );

    public static final WorkoutSession ENTITY_2 =
            new WorkoutSession(
                    UUID.fromString("ff252f6e-6e5d-11ed-a1eb-0242ac120002"),
                    false,
                    workoutPlanSnapshot(),
                    LocalDateTime.of(2016, 6, 22, 19, 10, 25),
                    "Medium training"
            );

    public static final WorkoutSession ENTITY_3 =
            new WorkoutSession(
                    UUID.fromString("05bf5a98-6e5e-11ed-a1eb-0242ac120002"),
                    false,
                    workoutPlanSnapshot(),
                    LocalDateTime.of(2016, 6, 22, 19, 10, 25),
                    "Hard training"
            );

    private static final LocalDateTime SESSION_DATE_TIME = LocalDateTime.of(2021, 12, 1, 1, 2);

    @Override
    public WorkoutSession entity() {
        return ENTITY_1;
    }

    @Override
    public List<WorkoutSession> entities() {
        return List.of(ENTITY_1, ENTITY_2, ENTITY_3);
    }

    @Override
    public WorkoutSession requestEntity(boolean isNew) {
        return new WorkoutSession(
                isNew ? null : ENTITY_1.id(),
                isNew,
                workoutPlanSnapshot(),
                SESSION_DATE_TIME,
                "request note"
        );
    }

    @Override
    public List<WorkoutSession> invalids(boolean isNew) {
        return List.of(
                new WorkoutSession(
                        isNew ? null : ENTITY_1.id(),
                        workoutPlanSnapshot(),
                        null,
                        "request note"
                ),
                new WorkoutSession(
                        isNew ? null : ENTITY_1.id(),
                        null,
                        SESSION_DATE_TIME,
                        "request note"
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
