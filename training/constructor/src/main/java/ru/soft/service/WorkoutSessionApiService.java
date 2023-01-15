package ru.soft.service;

import org.springframework.stereotype.Service;
import ru.soft.common.to.WorkoutSessionTo;
import ru.soft.data.model.Program;

@Service
public class WorkoutSessionApiService extends AbstractApiService<Program, WorkoutSessionTo> {
}
