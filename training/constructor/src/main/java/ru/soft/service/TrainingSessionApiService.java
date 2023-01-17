package ru.soft.service;

import org.springframework.stereotype.Service;
import ru.soft.common.to.TrainingSessionTo;
import ru.soft.data.model.TrainingSession;

@Service
public class TrainingSessionApiService extends AbstractApiService<TrainingSession, TrainingSessionTo> {
}
