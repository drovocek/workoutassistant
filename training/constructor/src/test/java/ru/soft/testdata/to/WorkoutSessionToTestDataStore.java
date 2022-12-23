package ru.soft.testdata.to;

import org.springframework.stereotype.Component;
import ru.soft.common.to.WorkoutSessionTo;
import ru.soft.testdata.TestDataStore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static ru.soft.testdata.snapshot.TestSnapshotStore.workoutPlanSnapshot;

@Component
public class WorkoutSessionToTestDataStore implements TestDataStore<WorkoutSessionTo> {

    private static final LocalDateTime SESSION_DATE_TIME = LocalDateTime.of(2021, 12, 1, 1, 2);

    @Override
    public WorkoutSessionTo entity(boolean isNew) {
        return new WorkoutSessionTo(
                isNew ? null : UUID.fromString("f386d086-6e5d-11ed-a1eb-0242ac120002"),
                workoutPlanSnapshot(),
                LocalDateTime.of(2016, 6, 22, 19, 10, 25),
                "Easy training"
        );
    }

    @Override
    public List<WorkoutSessionTo> entities(boolean isNew) {
        return List.of(
                entity(isNew),
                new WorkoutSessionTo(
                        isNew ? null : UUID.fromString("ff252f6e-6e5d-11ed-a1eb-0242ac120002"),
                        workoutPlanSnapshot(),
                        LocalDateTime.of(2016, 6, 22, 19, 10, 25),
                        "Medium training"
                ),
                new WorkoutSessionTo(
                        isNew ? null : UUID.fromString("05bf5a98-6e5e-11ed-a1eb-0242ac120002"),
                        workoutPlanSnapshot(),
                        LocalDateTime.of(2016, 6, 22, 19, 10, 25),
                        "Hard training"
                )
        );
    }

    @Override
    public WorkoutSessionTo requestEntity(boolean isNew) {
        return new WorkoutSessionTo(
                isNew ? null : newId(),
                workoutPlanSnapshot(),
                SESSION_DATE_TIME,
                "request note"
        );
    }

    @Override
    public List<WorkoutSessionTo> invalids(boolean isNew) {
        return List.of(
                new WorkoutSessionTo(
                        isNew ? null : newId(),
                        workoutPlanSnapshot(),
                        null,
                        "request note"
                ),
                new WorkoutSessionTo(
                        isNew ? null : newId(),
                        null,
                        SESSION_DATE_TIME,
                        "request note"
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
