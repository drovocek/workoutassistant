package ru.soft.common.testdata.to;

import org.springframework.stereotype.Component;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.to.WorkoutTo;

import java.util.List;
import java.util.UUID;

import static ru.soft.common.testdata.snapshot.TestSnapshotStore.roundsSchema;

@Component
public class WorkoutPlanToTestDataStore implements TestDataStore<WorkoutTo> {

    public static final String DUPLICATE_TITLE = "Barbell squats plan title";

    public static WorkoutTo example(boolean isNew) {
        return new WorkoutTo(
                isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"),
                roundsSchema()
                , "Push-up plan title",
                "Push-up plan description"
        );
    }

    @Override
    public WorkoutTo entity(boolean isNew) {
        return example(isNew);
    }

    @Override
    public List<WorkoutTo> entities(boolean isNew) {
        return List.of(
                entity(isNew),
                new WorkoutTo(
                        isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"),
                        roundsSchema()
                        , "Barbell squats plan title",
                        "Barbell squats plan description"
                ),
                new WorkoutTo(
                        isNew ? null : UUID.fromString("5c83571c-7a52-11ed-a1eb-0242ac120002"),
                        roundsSchema(),
                        "Pull-up plan title",
                        "Pull-up plan description"
                )
        );
    }

    @Override
    public WorkoutTo requestEntity(boolean isNew) {
        return new WorkoutTo(
                isNew ? null : newId(),
                roundsSchema(),
                "request plan title",
                "request plan description"
        );
    }

    @Override
    public List<WorkoutTo> invalids(boolean isNew) {
        return List.of(
                new WorkoutTo(
                        isNew ? null : newId(),
                        roundsSchema(),
                        "",
                        "request plan description"
                ),
                new WorkoutTo(
                        isNew ? newId() : null,
                        roundsSchema(),
                        "request plan title",
                        "request plan description"
                ),
                new WorkoutTo(
                        isNew ? null : newId(),
                        null,
                        "request plan title",
                        "request plan description"
                )
        );
    }

    @Override
    public List<WorkoutTo> duplicates(boolean isNew) {
        return List.of(
                new WorkoutTo(
                        isNew ? null : newId(),
                        roundsSchema(),
                        DUPLICATE_TITLE,
                        "request plan description"
                )
        );
    }

    @Override
    public List<WorkoutTo> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
