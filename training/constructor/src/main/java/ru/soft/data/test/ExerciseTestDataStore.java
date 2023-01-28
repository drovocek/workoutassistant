package ru.soft.data.test;

import org.springframework.stereotype.Component;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.data.model.Exercise;

import java.util.List;
import java.util.UUID;

@Component
public class ExerciseTestDataStore implements TestDataStore<Exercise> {

    public static final String DUPLICATE_TITLE = "Barbell squat exercise title";

    public static Exercise example(boolean isNew) {
        return Exercise.builder()
                .id(isNew ? null : UUID.fromString("f386d086-6e5d-11ed-a1eb-0242ac120002"))
                .isNew(isNew)
                .title("Push-up exercise title")
                .note("Push-up exercise note")
                .build();
    }

    public static List<Exercise> examples(boolean isNew) {
        return List.of(
                example(isNew),
                Exercise.builder()
                        .id(isNew ? null : UUID.fromString("ff252f6e-6e5d-11ed-a1eb-0242ac120002"))
                        .isNew(isNew)
                        .title("Barbell squat exercise title")
                        .note("Barbell squat exercise note")
                        .build(),
                Exercise.builder()
                        .id(isNew ? null : UUID.fromString("05bf5a98-6e5e-11ed-a1eb-0242ac120002"))
                        .isNew(isNew)
                        .title("Pull-up exercise title")
                        .note("Pull-up exercise note")
                        .build()
        );
    }

    @Override
    public Exercise entity(boolean isNew) {
        return example(isNew);
    }

    @Override
    public List<Exercise> entities(boolean isNew) {
        return examples(isNew);
    }

    @Override
    public Exercise requestEntity(boolean isNew) {
        return Exercise.builder()
                .id(isNew ? null : newId())
                .isNew(isNew)
                .title("request exercise title")
                .note("request exercise note")
                .build();
    }

    @Override
    public List<Exercise> invalids(boolean isNew) {
        return List.of(
                Exercise.builder()
                        .id(isNew ? null : newId())
                        .isNew(isNew)
                        .title(null)
                        .note("request exercise note")
                        .build(),
                Exercise.builder()
                        .id(isNew ? null : newId())
                        .isNew(isNew)
                        .title("")
                        .note("request exercise note")
                        .build(),
                Exercise.builder()
                        .id(isNew ? newId() : null)
                        .isNew(isNew)
                        .title("request exercise title")
                        .note("request exercise note")
                        .build()
        );
    }

    @Override
    public List<Exercise> duplicates(boolean isNew) {
        return List.of(
                Exercise.builder()
                        .id(isNew ? null : newId())
                        .isNew(isNew)
                        .title(DUPLICATE_TITLE)
                        .note("request exercise note")
                        .build()
        );
    }

    @Override
    public List<Exercise> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
