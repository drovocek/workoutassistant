package ru.soft.web.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.soft.data.model.WorkoutPlan;
import ru.soft.data.repository.WorkoutPlanRepository;
import ru.soft.common.AppApi;
import ru.soft.web.mapper.TOMapper;
import ru.soft.common.to.WorkoutPlanTo;

@Slf4j
@Getter
@RequiredArgsConstructor
@RestController
@RequestMapping(
        value = AppApi.WorkoutPlan.URL,
        produces = MediaType.APPLICATION_JSON_VALUE)
class WorkoutPlanApiController extends AbstractApiController<WorkoutPlan, WorkoutPlanTo> {

    private final WorkoutPlanRepository repository;
    private final TOMapper<WorkoutPlan, WorkoutPlanTo> mapper;
}