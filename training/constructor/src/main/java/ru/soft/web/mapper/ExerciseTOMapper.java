package ru.soft.web.mapper;

import org.springframework.stereotype.Component;
import ru.soft.data.model.Exercise;
import ru.soft.common.to.ExerciseTo;

@Component
public class ExerciseTOMapper implements TOMapper<Exercise, ExerciseTo> {

    public ExerciseTo toTo(Exercise entity) {
        return new ExerciseTo(
                entity.getId(),
                entity.title(),
                entity.description(),
                entity.complexity()
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
