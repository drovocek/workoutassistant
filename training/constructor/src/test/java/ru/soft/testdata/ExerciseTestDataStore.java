package ru.soft.testdata;

import org.springframework.stereotype.Component;
import ru.soft.common.to.ExerciseTo;
import ru.soft.data.model.Exercise;
import ru.soft.web.mapper.TOMapper;

import java.util.List;
import java.util.UUID;

@Component
public final class ExerciseTestDataStore implements TestDataStore<Exercise, ExerciseTo> {

    private final TOMapper<Exercise, ExerciseTo> mapper;

    public ExerciseTestDataStore(TOMapper<Exercise, ExerciseTo> mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Exercise> entities() {
        return List.of(
                new Exercise(UUID.fromString("f386d086-6e5d-11ed-a1eb-0242ac120002"),
                        "Push-up title", "Push-up description", 1),
                new Exercise(UUID.fromString("ff252f6e-6e5d-11ed-a1eb-0242ac120002"),
                        "Barbell squat title", "Barbell squat description", 2),
                new Exercise(UUID.fromString("05bf5a98-6e5e-11ed-a1eb-0242ac120002"),
                        "Pull-up title", "Pull-up description", 3)
        );
    }

    @Override
    public List<ExerciseTo> tos() {
        return entities().stream()
                .map(this.mapper::toTo)
                .toList();
    }
}
