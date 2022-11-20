package ru.soft.web.utils;

import lombok.experimental.UtilityClass;
import ru.soft.data.model.Exercise;
import ru.soft.web.to.ExerciseTo;

@UtilityClass
public class ExerciseUtils {

    public static ExerciseTo toTo(Exercise exercise) {
        return ExerciseTo.builder()
                .id(exercise.getId())
                .title(exercise.getTitle())
                .description(exercise.getDescription())
                .complexity(exercise.getComplexity())
                .build();
    }

    public static Exercise createNewFromTo(ExerciseTo to) {
        return Exercise.builder()
                .id(to.getId())
                .title(to.getTitle())
                .description(to.getDescription())
                .complexity(to.getComplexity())
                .build();
    }
}
