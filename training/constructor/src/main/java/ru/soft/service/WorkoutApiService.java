package ru.soft.service;

import org.springframework.stereotype.Service;
import ru.soft.common.to.WorkoutTo;
import ru.soft.data.model.Workout;

@Service
public class WorkoutApiService extends AbstractApiService<Workout, WorkoutTo> {
}
