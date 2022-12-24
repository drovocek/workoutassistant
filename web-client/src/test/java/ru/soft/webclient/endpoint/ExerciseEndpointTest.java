package ru.soft.webclient.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import dev.hilla.Nonnull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.to.ExerciseTo;
import ru.soft.common.utils.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

//https://docs.spring.io/spring-cloud-contract/docs/current/reference/html/project-features.html#features-wiremock
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 9000)
public class ExerciseEndpointTest {

    @Autowired
    private ExerciseEndpoint endpoint;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private TestDataStore<ExerciseTo> dataStore;

    @Value("#{T(ru.soft.common.AppApi.Exercises).URL}")
    private String coreUrl;

    @BeforeEach
    void initJsonUtil() {
        JsonUtil.setMapper(this.mapper);
    }

    protected String coreUrl() {
        return this.coreUrl;
    }

    protected BaseEndpoint<ExerciseTo> endpoint() {
        return this.endpoint;
    }

    protected TestDataStore<ExerciseTo> dataStore() {
        return this.dataStore;
    }

    @Test
    void getAll() {
        List<ExerciseTo> expectedAll = dataStore().entities(false);

        stubFor(WireMock.get(coreUrl() + "/all")
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(JsonUtil.writeValue(expectedAll))));

        List<@Nonnull ExerciseTo> actualAll = endpoint().getAll();

        Assertions.assertEquals(expectedAll, actualAll);
    }

    @Test
    void get() {
        ExerciseTo expected = dataStore().entity(false);
        UUID expectedId = expected.id();

        stubFor(WireMock.get(coreUrl() + "/" + expectedId)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(JsonUtil.writeValue(expected))));

        ExerciseTo actual = endpoint().get(expectedId);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getNotFound() throws Exception {
//        perform(MockMvcRequestBuilders.get(getApiUrl() + '/' + UUID.randomUUID()))
//                .andDo(print())
//                .andExpect(status().isNotFound());
    }

    @Test
    void delete() {
//        delete(expectedId(), status().isNoContent());
    }

    @Test
    void deleteNotFound() {
//        delete(UUID.randomUUID(), status().isNotFound());
    }

    @Test
    void update() {
//        TO forUpdate = requestEntity(false);
//        update(forUpdate, status().isNoContent());
//
//        TO actual = this.mapper.toTo(this.repository.getExisted(expectedId()));
//        matcher().assertMatch(actual, forUpdate);
    }

    @Test
    void updateInvalids() {
//        invalids(false).forEach(invalid ->
//                update(invalid, status().isUnprocessableEntity()));
    }

    @Test
    void updateDuplicates() {
//        duplicates(false).forEach(duplicate ->
//                update(duplicate, status().isUnprocessableEntity()));
    }

    @Test
    void updateHtmlUnsafe() {
//        htmlUnsafe(false).forEach(htmlUnsafe ->
//                update(htmlUnsafe, status().isUnprocessableEntity()));
    }

    @Test
    void add() throws UnsupportedEncodingException {
//        TO forAdd = requestEntity(true);
//        ResultActions resultAction = add(forAdd, status().isCreated());
//
//        TO to = matcher().readFromJson(resultAction);
//        T actual = this.mapper.fromTo(to);
//        T expectedWithoutId = this.mapper.fromTo(forAdd);
//
//        Assertions.assertEquals(expectedWithoutId.withId(actual.id()), actual);
    }

    @Test
    void addInvalids() {
//        invalids(true).forEach(invalid ->
//                add(invalid, status().isUnprocessableEntity()));
    }

    @Test
    void addDuplicates() {
//        duplicates(true).forEach(duplicate ->
//                add(duplicate, status().isUnprocessableEntity()));
    }

    @Test
    void addHtmlUnsafe() {
//        htmlUnsafe(true).forEach(htmlUnsafe ->
//                add(htmlUnsafe, status().isUnprocessableEntity()));
    }
}
