package ru.soft.webclient.resources;

import org.springframework.cloud.openfeign.FeignClient;
import ru.soft.common.api.ApiController;
import ru.soft.common.to.ExerciseTo;

@FeignClient(name = "exercises", url = "${feign.url}#{T(ru.soft.common.AppApi.Exercise).URL}")
public interface ExerciseClient extends ApiController<ExerciseTo> {
}