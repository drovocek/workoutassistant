package ru.soft.web.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.soft.data.model.Exercise;
import ru.soft.data.repository.ExerciseRepository;
import ru.soft.common.AppApi;
import ru.soft.web.mapper.TOMapper;
import ru.soft.common.to.ExerciseTo;

@Slf4j
@Getter
@RequiredArgsConstructor
@RestController
@RequestMapping(
        value = AppApi.Exercise.URL,
        produces = MediaType.APPLICATION_JSON_VALUE)
class ExerciseApiController extends AbstractApiController<Exercise, ExerciseTo> {

    private final ExerciseRepository repository;
    private final TOMapper<Exercise, ExerciseTo> mapper;
}
