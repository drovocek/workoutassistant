package ru.soft.service;

import org.springframework.stereotype.Service;
import ru.soft.common.to.RoundTo;
import ru.soft.data.model.Round;
import ru.soft.data.repository.common.BaseRepository;
import ru.soft.web.mapper.TOMapper;

@Service
public class WorkoutApiService extends AbstractApiService<Round, RoundTo> {

    public WorkoutApiService(BaseRepository<Round> repository, TOMapper<Round, RoundTo> mapper) {
        super(repository, mapper);
    }
}
