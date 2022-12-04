package ru.soft.web.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.soft.data.model.Exercise;
import ru.soft.web.to.ExerciseTo;

@Slf4j
@UtilityClass
public class ExerciseUtils {

    public static ExerciseTo toTo(Exercise exercise) {
        return new ExerciseTo(
                exercise.getId(),
                exercise.title(),
                exercise.description(),
                exercise.complexity()
        );
    }

    public static Exercise createNewFromTo(ExerciseTo to) {
        return Exercise.builder()
                .id(to.id())
                .title(to.title())
                .description(to.description())
                .complexity(to.complexity())
                .isNew(to.id() == null)
                .build();
    }
}
