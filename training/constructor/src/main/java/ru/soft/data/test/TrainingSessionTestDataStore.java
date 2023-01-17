package ru.soft.data.test;

import org.springframework.stereotype.Component;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.data.model.TrainingSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static ru.soft.common.testdata.TestSchemaStore.workoutSchema;

@Component
public class TrainingSessionTestDataStore implements TestDataStore<TrainingSession> {

    private static final LocalDateTime SESSION_DATE_TIME = LocalDateTime.of(2021, 12, 1, 1, 2);

    public static TrainingSession example(boolean isNew) {
        return TrainingSession.builder()
                .id(isNew ? null : UUID.fromString("a34798c2-7ac8-11ed-a1eb-0242ac120002"))
                .isNew(isNew)
                .workoutSchema(workoutSchema())
                .title("Easy session title")
                .note("Easy session note")
                .dateTime(LocalDateTime.of(2024, 3, 20, 19, 10, 25))
                .build();
    }

    @Override
    public TrainingSession entity(boolean isNew) {
        return example(isNew);
    }

    @Override
    public List<TrainingSession> entities(boolean isNew) {
        return List.of(
                entity(isNew),
                TrainingSession.builder()
                        .id(isNew ? null : UUID.fromString("a9323cf6-7ac8-11ed-a1eb-0242ac120002"))
                        .isNew(isNew)
                        .workoutSchema(workoutSchema())
                        .dateTime(LocalDateTime.of(2024, 3, 20, 19, 10, 25))
                        .title("Medium session title")
                        .note("Medium session note")
                        .build(),
                TrainingSession.builder()
                        .id(isNew ? null : UUID.fromString("ae9b7996-7ac8-11ed-a1eb-0242ac120002"))
                        .isNew(isNew)
                        .workoutSchema(workoutSchema())
                        .dateTime(LocalDateTime.of(2024, 3, 20, 19, 10, 25))
                        .title("Hard session title")
                        .note("Hard session note")
                        .build()
        );
    }

    @Override
    public TrainingSession requestEntity(boolean isNew) {
        return TrainingSession.builder()
                .id(isNew ? null : newId())
                .isNew(isNew)
                .workoutSchema(workoutSchema())
                .dateTime(SESSION_DATE_TIME)
                .title("request session title")
                .note("request session note")
                .build();
    }

    @Override
    public List<TrainingSession> invalids(boolean isNew) {
        return List.of(
                TrainingSession.builder()
                        .id(isNew ? null : newId())
                        .isNew(isNew)
                        .workoutSchema(workoutSchema())
                        .dateTime(null)
                        .title("request session title")
                        .note("request session note")
                        .build(),
                TrainingSession.builder()
                        .id(isNew ? null : newId())
                        .isNew(isNew)
                        .workoutSchema(workoutSchema())
                        .dateTime(SESSION_DATE_TIME)
                        .title("request session title")
                        .note("request session note")
                        .build()
        );
    }

    @Override
    public List<TrainingSession> duplicates(boolean isNew) {
        return List.of();
    }

    @Override
    public List<TrainingSession> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
