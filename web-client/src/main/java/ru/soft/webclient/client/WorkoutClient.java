package ru.soft.webclient.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.soft.common.api.ApiController;
import ru.soft.common.to.WorkoutTo;
import ru.soft.webclient.client.exception.ApiControllerFallbackFactory;

//https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/#spring-cloud-feign-circuitbreaker
@FeignClient(name = "workout",
        url = "${feign.url}#{T(ru.soft.common.AppApi.Workouts).URL}",
        fallbackFactory = ApiControllerFallbackFactory.class)
public interface WorkoutClient extends ApiController<WorkoutTo> {
}