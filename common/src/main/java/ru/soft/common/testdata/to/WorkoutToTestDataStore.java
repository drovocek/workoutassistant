package ru.soft.common.testdata.to;

import org.springframework.stereotype.Component;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.to.WorkoutTo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static ru.soft.common.testdata.TestSchemaStore.workoutSchema;

@Component
public class WorkoutToTestDataStore implements TestDataStore<WorkoutTo> {

    private static final LocalDateTime SESSION_DATE_TIME = LocalDateTime.of(2021, 12, 1, 1, 2);

    public static WorkoutTo example(boolean isNew) {
        return WorkoutTo.builder()
                .id(isNew ? null : UUID.fromString("a34798c2-7ac8-11ed-a1eb-0242ac120002"))
                .workoutSchema(workoutSchema())
                .title("Easy session title")
                .note("Easy session note")
                .dateTime(LocalDateTime.of(2024, 3, 20, 19, 10, 25))
                .build();
    }

    @Override
    public WorkoutTo entity(boolean isNew) {
        return example(isNew);
    }

    @Override
    public List<WorkoutTo> entities(boolean isNew) {
        return List.of(
                entity(isNew),
                WorkoutTo.builder()
                        .id(isNew ? null : UUID.fromString("a9323cf6-7ac8-11ed-a1eb-0242ac120002"))
                        .workoutSchema(workoutSchema())
                        .dateTime(LocalDateTime.of(2024, 3, 20, 19, 10, 25))
                        .title("Medium session title")
                        .note("Medium session note")
                        .build(),
                WorkoutTo.builder()
                        .id(isNew ? null : UUID.fromString("ae9b7996-7ac8-11ed-a1eb-0242ac120002"))
                        .workoutSchema(workoutSchema())
                        .dateTime(LocalDateTime.of(2024, 3, 20, 19, 10, 25))
                        .title("Hard session title")
                        .note("Hard session note")
                        .build()
        );
    }

    @Override
    public WorkoutTo requestEntity(boolean isNew) {
        return WorkoutTo.builder()
                .id(isNew ? null : newId())
                .workoutSchema(workoutSchema())
                .dateTime(SESSION_DATE_TIME)
                .title("request session title")
                .note("request session note")
                .build();
    }

    @Override
    public List<WorkoutTo> invalids(boolean isNew) {
        return List.of(
                WorkoutTo.builder()
                        .id(isNew ? null : newId())
                        .workoutSchema(workoutSchema())
                        .dateTime(null)
                        .title("request session title")
                        .note("request session note")
                        .build(),
                WorkoutTo.builder()
                        .id(isNew ? null : newId())
                        .workoutSchema(null)
                        .dateTime(SESSION_DATE_TIME)
                        .title("request session title")
                        .note("request session note")
                        .build()
        );
    }

    @Override
    public List<WorkoutTo> duplicates(boolean isNew) {
        return List.of();
    }

    @Override
    public List<WorkoutTo> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
