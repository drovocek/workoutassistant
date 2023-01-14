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
        return new ExerciseTo(
                isNew ? null : UUID.fromString("f386d086-6e5d-11ed-a1eb-0242ac120002"),
                "Push-up exercise title",
                "Push-up exercise description",
                1
        );
    }

    public static List<ExerciseTo> examples(boolean isNew) {
        return List.of(
                example(isNew),
                new ExerciseTo(
                        isNew ? null : UUID.fromString("ff252f6e-6e5d-11ed-a1eb-0242ac120002"),
                        "Barbell squat exercise title",
                        "Barbell squat exercise description",
                        2
                ),
                new ExerciseTo(
                        isNew ? null : UUID.fromString("05bf5a98-6e5e-11ed-a1eb-0242ac120002"),
                        "Pull-up exercise title",
                        "Pull-up exercise description",
                        3
                )
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
        return new ExerciseTo(
                isNew ? null : newId(),
                "request exercise title",
                "request exercise description",
                5
        );
    }

    @Override
    public List<ExerciseTo> invalids(boolean isNew) {
        return List.of(
                new ExerciseTo(
                        isNew ? null : newId(),
                        "",
                        "request exercise description",
                        5
                ),
                new ExerciseTo(
                        isNew ? null : newId(),
                        "request exercise title",
                        "request exercise description",
                        100
                ),
                new ExerciseTo(
                        isNew ? newId() : null,
                        "request exercise title",
                        "request exercise description",
                        5
                ),
                new ExerciseTo(
                        isNew ? null : newId(),
                        null,
                        "request exercise description",
                        5
                ),
                new ExerciseTo(
                        isNew ? null : newId(),
                        "request exercise title",
                        "request exercise description",
                        -1
                )
        );
    }

    @Override
    public List<ExerciseTo> duplicates(boolean isNew) {
        return List.of(
                new ExerciseTo(
                        isNew ? null : newId(),
                        DUPLICATE_TITLE,
                        "request exercise description",
                        10
                )
        );
    }

    @Override
    public List<ExerciseTo> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
