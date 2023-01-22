package ru.soft.data.test;

import org.springframework.stereotype.Component;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.data.model.Round;

import java.util.List;
import java.util.UUID;

import static ru.soft.common.testdata.TestSchemaStore.workoutSchema;

@Component
public class RoundDataStore implements TestDataStore<Round> {

    public static final String DUPLICATE_TITLE = "Barbell squats plan title";

    public static Round example(boolean isNew) {
        return Round.builder()
                .id(isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"))
                .isNew(isNew)
                .workoutSchema(workoutSchema())
                .title("Push-up plan title")
                .note("Push-up plan note")
                .build();
    }

    @Override
    public Round entity(boolean isNew) {
        return example(isNew);
    }

    @Override
    public List<Round> entities(boolean isNew) {
        return List.of(
                entity(isNew),
                Round.builder()
                        .id(isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"))
                        .isNew(isNew)
                        .workoutSchema(workoutSchema())
                        .title("Barbell squats plan title")
                        .note("Barbell squats plan note")
                        .build(),
                Round.builder()
                        .id(isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"))
                        .isNew(isNew)
                        .workoutSchema(workoutSchema())
                        .title("Pull-up plan title")
                        .note("Pull-up plan note")
                        .build()
        );
    }

    @Override
    public Round requestEntity(boolean isNew) {
        return Round.builder()
                .id(isNew ? null : newId())
                .isNew(isNew)
                .workoutSchema(workoutSchema())
                .title("request plan title")
                .note("request plan note")
                .build();
    }

    @Override
    public List<Round> invalids(boolean isNew) {
        return List.of(
                Round.builder()
                        .id(isNew ? null : newId())
                        .isNew(isNew)
                        .workoutSchema(workoutSchema())
                        .title(null)
                        .note("request plan note")
                        .build(),
                Round.builder()
                        .id(isNew ? null : newId())
                        .isNew(isNew)
                        .workoutSchema(workoutSchema())
                        .title("")
                        .note("request plan note")
                        .build(),
                Round.builder()
                        .id(isNew ? newId() : null)
                        .isNew(isNew)
                        .workoutSchema(workoutSchema())
                        .title("request plan title")
                        .note("request plan note")
                        .build()
        );
    }

    @Override
    public List<Round> duplicates(boolean isNew) {
        return List.of(
                Round.builder()
                        .id(isNew ? null : newId())
                        .isNew(isNew)
                        .workoutSchema(workoutSchema())
                        .title(DUPLICATE_TITLE)
                        .note("request plan note")
                        .build()
        );
    }

    @Override
    public List<Round> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}