package ru.soft.service;

import org.springframework.stereotype.Service;
import ru.soft.common.to.WorkoutPlanTo;
import ru.soft.data.model.WorkoutPlan;

@Service
public class WorkoutPlanApiService extends AbstractApiService<WorkoutPlan, WorkoutPlanTo> {
}
