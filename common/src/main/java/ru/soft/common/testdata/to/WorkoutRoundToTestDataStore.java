package ru.soft.common.testdata.to;

import org.springframework.stereotype.Component;
import ru.soft.common.to.RoundTo;
import ru.soft.common.testdata.TestDataStore;

import java.util.List;
import java.util.UUID;

import static ru.soft.common.testdata.snapshot.TestSnapshotStore.stationsSchema;

@Component
public class WorkoutRoundToTestDataStore implements TestDataStore<RoundTo> {

    private static final String DUPLICATE_TITLE = "Strength round title";

    public static RoundTo example(boolean isNew) {
        return new RoundTo(
                isNew ? null : UUID.fromString("3cd77b78-7a52-11ed-a1eb-0242ac120002"),
                stationsSchema()
                , "Warm up round title",
                "Warm up round description"
        );
    }

    public static List<RoundTo> examples(boolean isNew) {
        return List.of(
                example(isNew),
                new RoundTo(
                        isNew ? null : UUID.fromString("425da3e2-7a52-11ed-a1eb-0242ac120002"),
                        stationsSchema()
                        , "Strength round title",
                        "Strength round description"
                ),
                new RoundTo(
                        isNew ? null : UUID.fromString("475617e4-7a52-11ed-a1eb-0242ac120002"),
                        stationsSchema()
                        , "Endurance round title",
                        "Endurance round description"
                )
        );
    }

    @Override
    public RoundTo entity(boolean isNew) {
        return example(isNew);
    }

    @Override
    public List<RoundTo> entities(boolean isNew) {
        return examples(isNew);
    }

    @Override
    public RoundTo requestEntity(boolean isNew) {
        return new RoundTo(
                isNew ? null : newId(),
                stationsSchema(),
                "request round title",
                "request round description"
        );
    }

    @Override
    public List<RoundTo> invalids(boolean isNew) {
        return List.of(
                new RoundTo(
                        isNew ? null : newId(),
                        stationsSchema(),
                        "",
                        "request round description"
                ),
                new RoundTo(
                        isNew ? newId() : null,
                        stationsSchema(),
                        "request round title",
                        "request round description"
                ),
                new RoundTo(
                        isNew ? null : newId(),
                        null,
                        "request round title",
                        "request round description"
                )
        );
    }

    @Override
    public List<RoundTo> duplicates(boolean isNew) {
        return List.of(
                new RoundTo(
                        isNew ? null : newId(),
                        stationsSchema(),
                        DUPLICATE_TITLE,
                        "request round description"
                )
        );
    }

    @Override
    public List<RoundTo> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
