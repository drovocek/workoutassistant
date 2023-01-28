package ru.soft.common.testdata.to;

import org.springframework.stereotype.Component;
import ru.soft.common.to.ExerciseTo;
import ru.soft.common.testdata.TestDataStore;

import java.util.List;
import java.util.UUID;

@Component
public class ExerciseToTestDataStore implements TestDataStore<ExerciseTo> {

    public static final String DUPLICATE_TITLE = "Barbell squat exercise title";

    public static ExerciseTo example(boolean isNew) {
        return ExerciseTo.builder()
                .id(isNew ? null : UUID.fromString("f386d086-6e5d-11ed-a1eb-0242ac120002"))
                .title("Push-up exercise title")
                .note("Push-up exercise note")
                .build();
    }

    public static List<ExerciseTo> examples(boolean isNew) {
        return List.of(
                example(isNew),
                ExerciseTo.builder()
                        .id(isNew ? null : UUID.fromString("ff252f6e-6e5d-11ed-a1eb-0242ac120002"))
                        .title("Barbell squat exercise title")
                        .note("Barbell squat exercise note")
                        .build(),
                ExerciseTo.builder()
                        .id(isNew ? null : UUID.fromString("05bf5a98-6e5e-11ed-a1eb-0242ac120002"))
                        .title("Pull-up exercise title")
                        .note("Pull-up exercise note")
                        .build()
        );
    }

    @Override
    public ExerciseTo entity(boolean isNew) {
        return example(isNew);
    }

    @Override
    public List<ExerciseTo> entities(boolean isNew) {
        return examples(isNew);
    }

    @Override
    public ExerciseTo requestEntity(boolean isNew) {
        return ExerciseTo.builder()
                .id(isNew ? null : newId())
                .title("request exercise title")
                .note("request exercise note")
                .build();
    }

    @Override
    public List<ExerciseTo> invalids(boolean isNew) {
        return List.of(
                ExerciseTo.builder()
                        .id(isNew ? null : newId())
                        .title(null)
                        .note("request exercise note")
                        .build(),
                ExerciseTo.builder()
                        .id(isNew ? null : newId())
                        .title("")
                        .note("request exercise note")
                        .build()
        );
    }

    @Override
    public List<ExerciseTo> duplicates(boolean isNew) {
        return List.of(
                ExerciseTo.builder()
                        .id(isNew ? null : newId())
                        .title(DUPLICATE_TITLE)
                        .note("request exercise note")
                        .build()
        );
    }

    @Override
    public List<ExerciseTo> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
