package ru.soft.web.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.soft.data.model.WorkoutRound;
import ru.soft.data.repository.WorkoutRoundRepository;
import ru.soft.web.to.WorkoutRoundTo;
import ru.soft.web.to.mapper.TOMapper;

@Slf4j
@Getter
@RequiredArgsConstructor
@RestController
@RequestMapping(
        value = WorkoutRoundApiController.REST_URL,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class WorkoutRoundApiController extends BaseApiController<WorkoutRound, WorkoutRoundTo> {

    static final String REST_URL = "/api/rounds";

    private final WorkoutRoundRepository repository;
    private final TOMapper<WorkoutRound, WorkoutRoundTo> mapper;
}
