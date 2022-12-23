package ru.soft.service;

import org.springframework.stereotype.Service;
import ru.soft.common.to.WorkoutResultTo;
import ru.soft.data.model.WorkoutResult;

@Service
public class WorkoutResultApiService extends AbstractApiService<WorkoutResult, WorkoutResultTo> {
}
