package ru.soft.data.test;

import org.springframework.stereotype.Component;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.data.model.Program;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static ru.soft.common.testdata.snapshot.TestSnapshotStore.planSnapshot;

@Component
public class WorkoutSessionTestDataStore implements TestDataStore<Program> {

    private static final LocalDateTime SESSION_DATE_TIME = LocalDateTime.of(2021, 12, 1, 1, 2);

    @Override
    public Program entity(boolean isNew) {
        return new Program(
                isNew ? null : UUID.fromString("a34798c2-7ac8-11ed-a1eb-0242ac120002"),
                isNew,
                planSnapshot(),
                LocalDateTime.of(2024, 3, 20, 19, 10, 25),
                "Easy session note"
        );
    }

    @Override
    public List<Program> entities(boolean isNew) {
        return List.of(
                entity(isNew),
                new Program(
                        isNew ? null : UUID.fromString("a9323cf6-7ac8-11ed-a1eb-0242ac120002"),
                        isNew,
                        planSnapshot(),
                        LocalDateTime.of(2024, 3, 20, 19, 10, 25),
                        "Medium session note"
                ),
                new Program(
                        isNew ? null : UUID.fromString("ae9b7996-7ac8-11ed-a1eb-0242ac120002"),
                        isNew,
                        planSnapshot(),
                        LocalDateTime.of(2024, 3, 20, 19, 10, 25),
                        "Hard session note"
                )
        );
    }

    @Override
    public Program requestEntity(boolean isNew) {
        return new Program(
                isNew ? null : newId(),
                isNew,
                planSnapshot(),
                SESSION_DATE_TIME,
                "request session note"
        );
    }

    @Override
    public List<Program> invalids(boolean isNew) {
        return List.of(
                new Program(
                        isNew ? null : newId(),
                        isNew,
                        planSnapshot(),
                        null,
                        "request session note"
                ),
                new Program(
                        isNew ? null : newId(),
                        isNew,
                        null,
                        SESSION_DATE_TIME,
                        "request session note"
                )
        );
    }

    @Override
    public List<Program> duplicates(boolean isNew) {
        return List.of();
    }

    @Override
    public List<Program> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
