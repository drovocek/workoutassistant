package ru.soft.common.testdata.to;

import org.springframework.stereotype.Component;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.to.WorkoutTo;

import java.util.List;
import java.util.UUID;

import static ru.soft.common.testdata.TestSchemaStore.workoutSchema;

@Component
public class WorkoutToTestDataStore implements TestDataStore<WorkoutTo> {

    public static final String DUPLICATE_TITLE = "Barbell squats plan title";

    public static WorkoutTo example(boolean isNew) {
        return WorkoutTo.builder()
                .id(isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"))
                .workoutSchema(workoutSchema())
                .title("Push-up plan title")
                .note("Push-up plan note")
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
                        .id(isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"))
                        .workoutSchema(workoutSchema())
                        .title(DUPLICATE_TITLE)
                        .note("Barbell squats plan note")
                        .build(),
                WorkoutTo.builder()
                        .id(isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"))
                        .workoutSchema(workoutSchema())
                        .title("Pull-up plan title")
                        .note("Pull-up plan note")
                        .build()
        );
    }

    @Override
    public WorkoutTo requestEntity(boolean isNew) {
        return WorkoutTo.builder()
                .id(isNew ? null : newId())
                .workoutSchema(workoutSchema())
                .title("request plan title")
                .note("request plan note")
                .build();
    }

    @Override
    public List<WorkoutTo> invalids(boolean isNew) {
        return List.of(
                WorkoutTo.builder()
                        .id(isNew ? null : newId())
                        .workoutSchema(null)
                        .title("request plan title")
                        .note("request plan note")
                        .build(),
                WorkoutTo.builder()
                        .id(isNew ? newId() : null)
                        .workoutSchema(workoutSchema())
                        .title("request plan title")
                        .note("request plan note")
                        .build(),
                WorkoutTo.builder()
                        .id(isNew ? null : newId())
                        .workoutSchema(workoutSchema())
                        .title("")
                        .note("request plan note")
                        .build()
        );
    }

    @Override
    public List<WorkoutTo> duplicates(boolean isNew) {
        return List.of(
                WorkoutTo.builder()
                        .id(isNew ? null : newId())
                        .workoutSchema(workoutSchema())
                        .title(DUPLICATE_TITLE)
                        .note("request plan note")
                        .build()
        );
    }

    @Override
    public List<WorkoutTo> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
