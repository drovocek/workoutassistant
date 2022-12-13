package ru.soft.web.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.soft.data.model.WorkoutSession;
import ru.soft.data.repository.WorkoutSessionRepository;
import ru.soft.web.mapper.TOMapper;
import ru.soft.web.to.WorkoutSessionTo;

@Slf4j
@Getter
@RequiredArgsConstructor
@RestController
@RequestMapping(
        value = WorkoutSessionApiController.REST_URL,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class WorkoutSessionApiController extends BaseApiController<WorkoutSession, WorkoutSessionTo> {

    static final String REST_URL = "/api/sessions";

    private final WorkoutSessionRepository repository;
    private final TOMapper<WorkoutSession, WorkoutSessionTo> mapper;
}
