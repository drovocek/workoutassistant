package ru.soft.web.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.soft.data.model.WorkoutResult;
import ru.soft.data.repository.WorkoutResultRepository;
import ru.soft.common.AppApi;
import ru.soft.web.mapper.TOMapper;
import ru.soft.common.to.WorkoutResultTo;

@Slf4j
@Getter
@RequiredArgsConstructor
@RestController
@RequestMapping(
        value = AppApi.WorkoutResult.URL,
        produces = MediaType.APPLICATION_JSON_VALUE)
class WorkoutResultApiController extends AbstractApiController<WorkoutResult, WorkoutResultTo> {

    private final WorkoutResultRepository repository;
    private final TOMapper<WorkoutResult, WorkoutResultTo> mapper;
}
