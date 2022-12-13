package ru.soft.web.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.soft.data.model.Exercise;
import ru.soft.data.repository.ExerciseRepository;
import ru.soft.web.mapper.TOMapper;
import ru.soft.web.to.ExerciseTo;

@Slf4j
@Getter
@RequiredArgsConstructor
@RestController
@RequestMapping(
        value = ExerciseApiController.REST_URL,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ExerciseApiController extends BaseApiController<Exercise, ExerciseTo> {

    static final String REST_URL = "/api/exercises";

    private final ExerciseRepository repository;
    private final TOMapper<Exercise, ExerciseTo> mapper;
}
