package ru.soft.service;

import org.springframework.stereotype.Service;
import ru.soft.common.to.WorkoutTo;
import ru.soft.data.model.Workout;
import ru.soft.data.repository.common.BaseRepository;
import ru.soft.web.mapper.TOMapper;

@Service
public class TrainingSessionApiService extends AbstractApiService<Workout, WorkoutTo> {

    public TrainingSessionApiService(BaseRepository<Workout> repository, TOMapper<Workout, WorkoutTo> mapper) {
        super(repository, mapper);
    }
}
