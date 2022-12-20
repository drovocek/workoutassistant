package ru.soft.testdata;

import ru.soft.common.data.snapshot.ExerciseSnapshot;
import ru.soft.common.to.ExerciseTo;
import ru.soft.data.model.Exercise;
import ru.soft.web.mapper.ExerciseTOMapper;
import ru.soft.web.mapper.TOMapper;

import java.util.List;
import java.util.UUID;

public final class ExerciseTestDataStore implements TestDataStore<Exercise, ExerciseTo> {

    private final static ExerciseTestDataStore INSTANCE = new ExerciseTestDataStore();

    public static ExerciseTestDataStore getInstance() {
        return INSTANCE;
    }

    private final static Exercise ENTITY =
            new Exercise(
                    UUID.fromString("f386d086-6e5d-11ed-a1eb-0242ac120002"),
                    "Push-up title",
                    "Push-up description", 1);

    private final TOMapper<Exercise, ExerciseTo> toMapper;

    private ExerciseTestDataStore() {
        this.toMapper = new ExerciseTOMapper();
    }

    @Override
    public Exercise entity() {
        return ENTITY;
    }

    @Override
    public List<Exercise> entities() {
        return List.of(
                entity(),
                new Exercise(UUID.fromString("ff252f6e-6e5d-11ed-a1eb-0242ac120002"),
                        "Barbell squat title", "Barbell squat description", 2),
                new Exercise(UUID.fromString("05bf5a98-6e5e-11ed-a1eb-0242ac120002"),
                        "Pull-up title", "Pull-up description", 3)
        );
    }

    @Override
    public ExerciseTo to() {
        return this.toMapper.toTo(ENTITY);
    }

    @Override
    public List<ExerciseTo> tos() {
        return entities().stream()
                .map(this.toMapper::toTo)
                .toList();
    }

    public static ExerciseSnapshot exerciseSnapshot() {
        return new ExerciseSnapshot(
                ENTITY.title(),
                ENTITY.description(),
                ENTITY.complexity()
        );
    }
}
