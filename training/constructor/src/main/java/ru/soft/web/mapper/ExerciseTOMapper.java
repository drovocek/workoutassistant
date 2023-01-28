package ru.soft.web.mapper;

import org.springframework.stereotype.Component;
import ru.soft.common.to.ExerciseTo;
import ru.soft.data.model.Exercise;

@Component
public class ExerciseTOMapper implements TOMapper<Exercise, ExerciseTo> {

    public ExerciseTo toTo(Exercise entity) {
        return ExerciseTo.builder()
                .id(entity.id())
                .title(entity.title())
                .note(entity.note())
                .build();
    }

    public Exercise fromTo(ExerciseTo to) {
        return Exercise.builder()
                .id(to.id())
                .isNew(to.id() == null)
                .title(to.title())
                .note(to.note())
                .build();
    }
}
