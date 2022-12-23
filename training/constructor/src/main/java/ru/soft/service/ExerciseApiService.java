package ru.soft.service;

import org.springframework.stereotype.Service;
import ru.soft.common.to.ExerciseTo;
import ru.soft.data.model.Exercise;

@Service
public class ExerciseApiService extends AbstractApiService<Exercise, ExerciseTo> {
}
