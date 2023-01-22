package ru.soft.service;

import org.springframework.stereotype.Service;
import ru.soft.common.to.RoundTo;
import ru.soft.data.model.Round;

@Service
public class WorkoutApiService extends AbstractApiService<Round, RoundTo> {
}
