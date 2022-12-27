package ru.soft.webclient.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.soft.common.api.ApiController;
import ru.soft.common.to.ExerciseTo;
import ru.soft.webclient.client.exception.ApiControllerFallbackFactory;

//https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/#spring-cloud-feign-circuitbreaker
@FeignClient(name = "exercises",
        url = "${feign.url}#{T(ru.soft.common.AppApi.Exercises).URL}",
        fallbackFactory = ApiControllerFallbackFactory.class)
public interface ExerciseClient extends ApiController<ExerciseTo> {
}