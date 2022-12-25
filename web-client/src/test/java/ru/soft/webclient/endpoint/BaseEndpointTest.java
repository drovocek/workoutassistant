package ru.soft.webclient.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import dev.hilla.Nonnull;
import dev.hilla.exception.EndpointException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import ru.soft.common.api.ApiController;
import ru.soft.common.data.HasId;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.utils.JsonUtil;

import java.util.List;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

//https://www.baeldung.com/java-feign-client-exception-handling
//https://docs.spring.io/spring-cloud-contract/docs/current/reference/html/project-features.html#features-wiremock
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 9000)
abstract class BaseEndpointTest<TO extends HasId> {

    @Autowired
    private ApiController<TO> endpoint;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void initJsonUtil() {
        JsonUtil.setMapper(this.mapper);
    }

    protected abstract String rootUrl();

    protected abstract TestDataStore<TO> dataStore();

    protected ApiController<TO> endpoint() {
        return this.endpoint;
    }

    @Test
    @DisplayName("должен возвращать все сохраненные экземпляры сущности")
    void getAll() {
        List<TO> expectedAll = dataStore().entities(false);

        stubFor(WireMock.get(rootUrl() + "/all")
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(JsonUtil.writeValue(expectedAll))));

        List<@Nonnull TO> actualAll = endpoint().getAll();

        Assertions.assertEquals(expectedAll, actualAll);
    }

    @Test
    @DisplayName("должен возвращать экземпляр сущности с запрашиваемым id")
    void get() {
        TO expected = dataStore().entity(false);
        UUID expectedId = expected.id();

        stubFor(WireMock.get(rootUrl() + "/" + expectedId)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(JsonUtil.writeValue(expected))));

        TO actual = endpoint().get(expectedId);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getNotFound() {
        UUID notExitingId = UUID.randomUUID();
        stubFor(WireMock.get(rootUrl() + "/" + notExitingId)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())));

        EndpointException exception =
                Assertions.assertThrows(EndpointException.class, () -> endpoint().get(notExitingId));

        Assertions.assertTrue(exception.getMessage().contains("[404 Not Found]"));
    }

    @Test
    void delete() {
        UUID exitingId = UUID.randomUUID();
        stubFor(WireMock.delete(rootUrl() + "/" + exitingId)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NO_CONTENT.value())));

        endpoint().delete(exitingId);
    }

    @Test
    void deleteNotFound() {
        UUID notExitingId = UUID.randomUUID();
        stubFor(WireMock.delete(rootUrl() + "/" + notExitingId)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())));

        EndpointException exception =
                Assertions.assertThrows(EndpointException.class, () -> endpoint().delete(notExitingId));

        Assertions.assertTrue(exception.getMessage().contains("[404 Not Found]"));
    }

    @Test
    void update() {
        stubFor(WireMock.put(rootUrl())
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NO_CONTENT.value())));

        TO forUpdate = dataStore().requestEntity(false);
        endpoint().update(forUpdate);
    }

    @Test
    void updateInvalids() {
        dataStore().invalids(false)
                .forEach(invalid -> {
                    stubFor(WireMock.put(rootUrl())
                            .withRequestBody(equalToJson(JsonUtil.writeValue(invalid)))
                            .willReturn(aResponse()
                                    .withStatus(HttpStatus.UNPROCESSABLE_ENTITY.value())));

                    EndpointException exception =
                            Assertions.assertThrows(EndpointException.class, () -> endpoint().update(invalid));

                    Assertions.assertTrue(exception.getMessage().contains("[422 Unprocessable Entity]"));
                });
    }

    @Test
    void updateDuplicates() {
        dataStore().duplicates(false)
                .forEach(duplicate -> {
                    stubFor(WireMock.put(rootUrl())
                            .withRequestBody(equalToJson(JsonUtil.writeValue(duplicate)))
                            .willReturn(aResponse()
                                    .withStatus(HttpStatus.UNPROCESSABLE_ENTITY.value())));


                    EndpointException exception =
                            Assertions.assertThrows(EndpointException.class, () -> endpoint().update(duplicate));

                    Assertions.assertTrue(exception.getMessage().contains("[422 Unprocessable Entity]"));
                });
    }
//
//    @Test
//    void updateHtmlUnsafe() {
//        htmlUnsafe(false).forEach(htmlUnsafe ->
//                update(htmlUnsafe, status().isUnprocessableEntity()));
//    }
//
//    @Test
//    void add() throws UnsupportedEncodingException {
//        TO forAdd = requestEntity(true);
//        ResultActions resultAction = add(forAdd, status().isCreated());
//
//        TO to = matcher().readFromJson(resultAction);
//        T actual = this.mapper.fromTo(to);
//        T expectedWithoutId = this.mapper.fromTo(forAdd);
//
//        Assertions.assertEquals(expectedWithoutId.withId(actual.id()), actual);
//    }
//
//    @Test
//    void addInvalids() {
//        invalids(true).forEach(invalid ->
//                add(invalid, status().isUnprocessableEntity()));
//    }
//
//    @Test
//    void addDuplicates() {
//        duplicates(true).forEach(duplicate ->
//                add(duplicate, status().isUnprocessableEntity()));
//    }
//
//    @Test
//    void addHtmlUnsafe() {
//        htmlUnsafe(true).forEach(htmlUnsafe ->
//                add(htmlUnsafe, status().isUnprocessableEntity()));
//    }
}
