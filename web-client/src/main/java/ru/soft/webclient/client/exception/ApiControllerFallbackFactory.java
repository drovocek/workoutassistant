package ru.soft.webclient.client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hilla.exception.EndpointException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.soft.common.api.ApiController;
import ru.soft.common.data.HasId;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class ApiControllerFallbackFactory implements FallbackFactory<ApiController<HasId>> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public ApiController<HasId> create(Throwable cause) {

        log.error("An exception occurred when calling the UserSessionClient", cause);
        return new ApiController<>() {

            @Override
            public HasId get(UUID id) {
                throwEndpointException(cause);
                return null;
            }

            @Override
            public List<HasId> getAll() {
                throwEndpointException(cause);
                return null;
            }

            @Override
            public Page<HasId> getPage(Pageable pageable) {
                throwEndpointException(cause);
                return null;
            }

            @Override
            public long count() {
                throwEndpointException(cause);
                return 0;
            }

            @Override
            public void delete(UUID id) {
                throwEndpointException(cause);
            }

            @Override
            public HasId add(HasId hasId) {
                throwEndpointException(cause);
                return null;
            }

            @Override
            public void update(HasId hasId) {
                throwEndpointException(cause);
            }
        };
    }

    private void throwEndpointException(Throwable cause) {
        String message;
        if (cause instanceof FeignException feignException) {
            log.info("Endpoint catch bad response with status code - {}", feignException.status());
            message = extractServerResponse(feignException)
                    .map(serverResponse -> serverResponse.message)
                    .orElse(feignException.getLocalizedMessage());
        } else {
            message = cause.getMessage();
        }

        log.info("Throw to client EndpointException with message - {}", message);
        throw new EndpointException(message);
    }

    record ServerResponse(String message, int status) {
    }

    private Optional<ServerResponse> extractServerResponse(FeignException feignException) {
        try {
            Optional<ByteBuffer> responseOpt = feignException.responseBody();
            if (responseOpt.isEmpty() || !responseOpt.get().hasArray()) {
                return Optional.empty();
            }
            ByteBuffer byteBuffer = responseOpt.get();
            String response = new String(byteBuffer.array(), Charset.defaultCharset());

            ServerResponse serverResponse = this.mapper.readValue(response, ServerResponse.class);
            return Optional.of(serverResponse);
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}