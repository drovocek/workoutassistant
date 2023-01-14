package ru.soft.data.test;

import org.springframework.stereotype.Component;
import ru.soft.data.model.Round;
import ru.soft.common.testdata.TestDataStore;

import java.util.List;
import java.util.UUID;

import static ru.soft.common.testdata.snapshot.TestSnapshotStore.stationsSchema;

@Component
public class WorkoutRoundTestDataStore implements TestDataStore<Round> {

    private static final String DUPLICATE_TITLE = "Strength round title";

    @Override
    public Round entity(boolean isNew) {
        return new Round(
                isNew ? null : UUID.fromString("3cd77b78-7a52-11ed-a1eb-0242ac120002"),
                isNew,
                stationsSchema()
                , "Warm up round title",
                "Warm up round description"
        );
    }

    @Override
    public List<Round> entities(boolean isNew) {
        return List.of(
                entity(isNew),
                new Round(
                        isNew ? null : UUID.fromString("425da3e2-7a52-11ed-a1eb-0242ac120002"),
                        isNew,
                        stationsSchema()
                        , "Strength round title",
                        "Strength round description"
                ),
                new Round(
                        isNew ? null : UUID.fromString("475617e4-7a52-11ed-a1eb-0242ac120002"),
                        isNew,
                        stationsSchema()
                        , "Endurance round title",
                        "Endurance round description"
                )
        );
    }

    @Override
    public Round requestEntity(boolean isNew) {
        return new Round(
                isNew ? null : newId(),
                isNew,
                stationsSchema(),
                "request round title",
                "request round description"
        );
    }

    @Override
    public List<Round> invalids(boolean isNew) {
        return List.of(
                new Round(
                        isNew ? null : newId(),
                        isNew,
                        stationsSchema(),
                        "",
                        "request round description"
                ),
                new Round(
                        isNew ? newId() : null,
                        isNew,
                        stationsSchema(),
                        "request round title",
                        "request round description"
                ),
                new Round(
                        isNew ? null : newId(),
                        isNew,
                        null,
                        "request round title",
                        "request round description"
                )
        );
    }

    @Override
    public List<Round> duplicates(boolean isNew) {
        return List.of(
                new Round(
                        isNew ? null : newId(),
                        isNew,
                        stationsSchema(),
                        DUPLICATE_TITLE,
                        "request round description"
                )
        );
    }

    @Override
    public List<Round> htmlUnsafe(boolean isNew) {
        return List.of();
    }
}
