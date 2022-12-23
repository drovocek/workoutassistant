package ru.soft.service;

import org.springframework.stereotype.Service;
import ru.soft.common.to.WorkoutSessionTo;
import ru.soft.data.model.WorkoutSession;

@Service
public class WorkoutSessionApiService extends AbstractApiService<WorkoutSession, WorkoutSessionTo> {
}
