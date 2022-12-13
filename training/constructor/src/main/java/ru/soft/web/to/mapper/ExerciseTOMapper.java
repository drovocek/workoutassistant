package ru.soft.web.to.mapper;

import org.springframework.stereotype.Component;
import ru.soft.data.model.Exercise;
import ru.soft.web.to.ExerciseTo;

@Component
public class ExerciseTOMapper implements TOMapper<Exercise, ExerciseTo> {

    public ExerciseTo toTo(Exercise exercise) {
        return new ExerciseTo(
                exercise.getId(),
                exercise.title(),
                exercise.description(),
                exercise.complexity()
        );
    }

    public Exercise fromTo(ExerciseTo to) {
        return Exercise.builder()
                .id(to.id())
                .isNew(to.id() == null)
                .title(to.title())
                .description(to.description())
                .complexity(to.complexity())
                .build();
    }
}
