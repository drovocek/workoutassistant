package ru.soft.common.testdata.to;

import org.springframework.stereotype.Component;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.to.RoundTo;

import java.util.List;
import java.util.UUID;

import static ru.soft.common.testdata.TestSchemaStore.workoutSchema;

@Component
public class RoundToTestDataStore implements TestDataStore<RoundTo> {

    public static final String DUPLICATE_TITLE = "Barbell squats plan title";

    public static RoundTo example(boolean isNew) {
        return RoundTo.builder()
                .id(isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"))
                .workoutSchema(workoutSchema())
                .title("Push-up plan title")
                .note("Push-up plan note")
                .build();
    }

    @Override
    public RoundTo entity(boolean isNew) {
        return example(isNew);
    }

    @Override
    public List<RoundTo> entities(boolean isNew) {
        return List.of(
                entity(isNew),
                RoundTo.builder()
                        .id(isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"))
                        .workoutSchema(workoutSchema())
                        .title(DUPLICATE_TITLE)
                        .note("Barbell squats plan note")
                        .build(),
                RoundTo.builder()
                        .id(isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"))
                        .workoutSchema(workoutSchema())
                        .title("Pull-up plan title")
                        .note("Pull-up plan note")
                        .build()
        );
    }

    @Override
    public RoundTo requestEntity(boolean isNew) {
        return RoundTo.builder()
                .id(isNew ? null : newId())
                .workoutSchema(workoutSchema())
                .title("request plan title")
                .note("request plan note")
                .build();
    }

    @Override
    public List<RoundTo> invalids(boolean isNew) {
        return List.of(
                RoundTo.builder()
                        .id(isNew ? null : newId())
                        .workoutSchema(null)
                        .title("request plan title")
                        .note("request plan note")
                        .build(),
                RoundTo.builder()
                        .id(isNew ? null : newId())
                        .workoutSchema(workoutSchema())
                        .title("")
                        .note("request plan note")
                        .build()
        );
    }

    @Override
    public List<RoundTo> duplicates(boolean isNew) {
        return List.of(
                RoundTo.builder()
                        .id(isNew ? null : newId())
                        .workoutSchema(workoutSchema())
                        .title(DUPLICATE_TITLE)
                        .note("request plan note")
                        .build()
        );
    }

    @Override
    public List<RoundTo> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
