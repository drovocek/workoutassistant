package ru.soft.data.test;

import org.springframework.stereotype.Component;
import ru.soft.data.model.Exercise;
import ru.soft.common.testdata.TestDataStore;

import java.util.List;
import java.util.UUID;

@Component
public class ExerciseTestDataStore implements TestDataStore<Exercise> {

    private static final String DUPLICATE_TITLE = "Barbell squat exercise title";

    @Override
    public Exercise entity(boolean isNew) {
        return  new Exercise(
                isNew? null: UUID.fromString("f386d086-6e5d-11ed-a1eb-0242ac120002"),
                isNew,
                "Push-up exercise title",
                "Push-up exercise description",
                1
        );
    }

    @Override
    public List<Exercise> entities(boolean isNew) {
        return List.of(
                entity(isNew),
                new Exercise(
                        isNew? null: UUID.fromString("ff252f6e-6e5d-11ed-a1eb-0242ac120002"),
                        isNew,
                        "Barbell squat exercise title",
                        "Barbell squat exercise description",
                        2
                ),
                new Exercise(
                        isNew? null: UUID.fromString("05bf5a98-6e5e-11ed-a1eb-0242ac120002"),
                        isNew,
                        "Pull-up exercise title",
                        "Pull-up exercise description",
                        3
                )
        );
    }

    @Override
    public Exercise requestEntity(boolean isNew) {
        return new Exercise(
                isNew ? null : newId(),
                isNew,
                "request exercise title",
                "request exercise description",
                10
        );
    }

    @Override
    public List<Exercise> invalids(boolean isNew) {
        return List.of(
                new Exercise(
                        isNew ? null : newId(),
                        isNew,
                        "",
                        "request exercise description",
                        10
                ),
                new Exercise(
                        isNew ? null : newId(),
                        isNew,
                        "request exercise title",
                        "request exercise description",
                        100
                ),
                new Exercise(
                        isNew ? newId() : null,
                        isNew,
                        "request exercise title",
                        "request exercise description",
                        10
                ),
                new Exercise(
                        isNew ? null : newId(),
                        isNew,
                        null,
                        "request exercise description",
                        10
                ),
                new Exercise(
                        isNew ? null : newId(),
                        isNew,
                        "request exercise title",
                        "request exercise description",
                        -1
                )
        );
    }

    @Override
    public List<Exercise> duplicates(boolean isNew) {
        return List.of(
                new Exercise(
                        isNew ? null : newId(),
                        isNew,
                        DUPLICATE_TITLE,
                        "request exercise description",
                        10
                )
        );
    }

    @Override
    public List<Exercise> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
