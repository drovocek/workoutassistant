package ru.soft.web.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.soft.common.AppApi;
import ru.soft.common.to.WorkoutSessionTo;

@Slf4j
@Getter
@RequiredArgsConstructor
@RestController
@RequestMapping(
        value = AppApi.WorkoutSessions.URL,
        produces = MediaType.APPLICATION_JSON_VALUE)
class WorkoutSessionApiController extends AbstractApiController<WorkoutSessionTo> {
}
