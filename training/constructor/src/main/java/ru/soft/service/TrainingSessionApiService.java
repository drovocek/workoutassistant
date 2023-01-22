package ru.soft.service;

import org.springframework.stereotype.Service;
import ru.soft.common.to.WorkoutTo;
import ru.soft.data.model.Workout;

@Service
public class TrainingSessionApiService extends AbstractApiService<Workout, WorkoutTo> {
}
