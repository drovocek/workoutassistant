package ru.soft.service;

import org.springframework.stereotype.Service;
import ru.soft.common.to.ExerciseTo;
import ru.soft.data.model.Exercise;
import ru.soft.data.repository.common.BaseRepository;
import ru.soft.web.mapper.TOMapper;

@Service
public class ExerciseApiService extends AbstractApiService<Exercise, ExerciseTo> {

    public ExerciseApiService(BaseRepository<Exercise> repository, TOMapper<Exercise, ExerciseTo> mapper) {
        super(repository, mapper);
    }
}
