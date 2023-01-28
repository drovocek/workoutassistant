package ru.soft.webclient.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.soft.common.api.ApiController;
import ru.soft.common.to.RoundTo;
import ru.soft.webclient.client.exception.ApiControllerFallbackFactory;

//https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/#spring-cloud-feign-circuitbreaker
@FeignClient(name = "round",
        url = "${feign.url}#{T(ru.soft.common.AppApi.Rounds).URL}",
        fallbackFactory = ApiControllerFallbackFactory.class)
public interface RoundClient extends ApiController<RoundTo> {
}