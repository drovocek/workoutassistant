package ru.soft.data.test;

import org.springframework.stereotype.Component;
import ru.soft.data.model.Workout;
import ru.soft.common.testdata.TestDataStore;

import java.util.List;
import java.util.UUID;

import static ru.soft.common.testdata.snapshot.TestSnapshotStore.roundsSchema;

@Component
public class WorkoutPlanTestDataStore implements TestDataStore<Workout> {

    private static final String DUPLICATE_TITLE = "Barbell squats plan title";

    @Override
    public Workout entity(boolean isNew) {
        return new Workout(
                isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"),
                isNew,
                roundsSchema()
                , "Push-up plan title",
                "Push-up plan description"
        );
    }

    @Override
    public List<Workout> entities(boolean isNew) {
        return List.of(
                entity(isNew),
                new Workout(
                        isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"),
                        isNew,
                        roundsSchema()
                        , "Barbell squats plan title",
                        "Barbell squats plan description"
                ),
                new Workout(
                        isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"),
                        isNew,
                        roundsSchema(),
                        "Pull-up plan title",
                        "Pull-up plan description"
                )
        );
    }

    @Override
    public Workout requestEntity(boolean isNew) {
        return new Workout(
                isNew ? null : newId(),
                isNew,
                roundsSchema(),
                "request plan title",
                "request plan description"
        );
    }

    @Override
    public List<Workout> invalids(boolean isNew) {
        return List.of(
                new Workout(
                        isNew ? null : newId(),
                        isNew,
                        roundsSchema(),
                        "",
                        "request plan description"
                ),
                new Workout(
                        isNew ? newId() : null,
                        isNew,
                        roundsSchema(),
                        "request plan title",
                        "request plan description"
                ),
                new Workout(
                        isNew ? null : newId(),
                        isNew,
                        null,
                        "request plan title",
                        "request plan description"
                )
        );
    }

    @Override
    public List<Workout> duplicates(boolean isNew) {
        return List.of(
                new Workout(
                        isNew ? null : newId(),
                        isNew,
                        roundsSchema(),
                        DUPLICATE_TITLE,
                        "request plan description"
                )
        );
    }

    @Override
    public List<Workout> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
