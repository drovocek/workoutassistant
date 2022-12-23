package ru.soft.testdata.to;

import org.springframework.stereotype.Component;
import ru.soft.common.to.ExerciseTo;
import ru.soft.testdata.TestDataStore;

import java.util.List;
import java.util.UUID;

@Component
public class ExerciseToTestDataStore implements TestDataStore<ExerciseTo> {

    public static final String DUPLICATE_TITLE = "Barbell squat title";

    @Override
    public ExerciseTo entity(boolean isNew) {
        return new ExerciseTo(
                isNew ? null : UUID.fromString("f386d086-6e5d-11ed-a1eb-0242ac120002"),
                "Push-up title",
                "Push-up description",
                1
        );
    }

    @Override
    public List<ExerciseTo> entities(boolean isNew) {
        return List.of(
                entity(isNew),
                new ExerciseTo(
                        isNew ? null : UUID.fromString("ff252f6e-6e5d-11ed-a1eb-0242ac120002"),
                        "Barbell squat title",
                        "Barbell squat description",
                        2
                ),
                new ExerciseTo(
                        isNew ? null : UUID.fromString("05bf5a98-6e5e-11ed-a1eb-0242ac120002"),
                        "Pull-up title",
                        "Pull-up description",
                        3
                )
        );
    }

    @Override
    public ExerciseTo requestEntity(boolean isNew) {
        return new ExerciseTo(
                isNew ? null : NEW_ID,
                "request title",
                "request description",
                10
        );
    }

    @Override
    public List<ExerciseTo> invalids(boolean isNew) {
        return List.of(
                new ExerciseTo(
                        isNew ? null : newId(),
                        "",
                        "request description",
                        10
                ),
                new ExerciseTo(
                        isNew ? null : newId(),
                        "request title",
                        "request description",
                        100
                ),
                new ExerciseTo(
                        isNew ? newId() : null,
                        "request title",
                        "request description",
                        10
                ),
                new ExerciseTo(
                        isNew ? null : newId(),
                        null,
                        "request description",
                        10
                ),
                new ExerciseTo(
                        isNew ? null : newId(),
                        "request title",
                        "request description",
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
                        "request description",
                        10
                )
        );
    }

    @Override
    public List<ExerciseTo> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
