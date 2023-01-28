package ru.soft.webclient.client.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.hilla.exception.EndpointException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Optional;

//@Slf4j
//@Component
public class FeignExceptionHandler implements ErrorDecoder {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        if (HttpStatus.UNPROCESSABLE_ENTITY.equals(HttpStatus.resolve(response.status()))) {
            return readServerResponse(response)
                    .map(s -> s.message)
                    .map(EndpointException::new)
                    .orElse(null);
        }

        return null;
    }

    public record ServerResponse(String message, int status) {
    }

    private Optional<ServerResponse> readServerResponse(Response response) {
        try (Reader reader = response.body().asReader(Charset.defaultCharset())) {
            ServerResponse serverResponse = this.mapper.readValue(reader, ServerResponse.class);
            return Optional.of(serverResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}