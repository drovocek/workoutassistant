package ru.soft.web.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.soft.common.AppApi;
import ru.soft.common.to.WorkoutTo;
import ru.soft.service.BaseApiService;

@Slf4j
@Getter
@RestController
@RequestMapping(
        value = AppApi.Workouts.URL,
        produces = MediaType.APPLICATION_JSON_VALUE)
class WorkoutApiController extends AbstractApiController<WorkoutTo> {

    public WorkoutApiController(BaseApiService<WorkoutTo> service) {
        super(service);
    }
}
