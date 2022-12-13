package ru.soft.web.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.soft.data.model.WorkoutResult;
import ru.soft.data.repository.WorkoutResultRepository;
import ru.soft.web.mapper.TOMapper;
import ru.soft.web.to.WorkoutResultTo;

@Slf4j
@Getter
@RequiredArgsConstructor
@RestController
@RequestMapping(
        value = WorkoutResultApiController.REST_URL,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class WorkoutResultApiController extends BaseApiController<WorkoutResult, WorkoutResultTo> {

    static final String REST_URL = "/api/workouts/results";

    private final WorkoutResultRepository repository;
    private final TOMapper<WorkoutResult, WorkoutResultTo> mapper;
}
