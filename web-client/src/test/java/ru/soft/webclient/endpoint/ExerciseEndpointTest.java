package ru.soft.webclient.endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import dev.hilla.Nonnull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import ru.soft.common.to.ExerciseTo;

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

    @Value("#{T(ru.soft.common.AppApi.Exercise).URL}")
    private String coreUrl;

    protected UUID expectedId(){
        return UUID.randomUUID();
    }

    private String asJson(List<Object> objects) {
        return asJson(objects);
    }

    private String asJson(Object object) {
        try {
            return this.mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAll() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuid3 = UUID.randomUUID();
        ExerciseTo expected1 = new ExerciseTo(uuid1, "title1", "description1", 1);
        ExerciseTo expected2 = new ExerciseTo(uuid2, "title2", "description2", 2);
        ExerciseTo expected3 = new ExerciseTo(uuid3, "title3", "description3", 3);
        List<ExerciseTo> expectedAll = List.of(expected1, expected2, expected3);

        stubFor(WireMock.get(coreUrl + "/all")
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(asJson(expectedAll))));
        List<@Nonnull ExerciseTo> actualAll = this.endpoint.getAll();

        Assertions.assertEquals(expectedAll, actualAll);
    }

    @Test
    void get() throws Exception {
        UUID uuid = expectedId();
        ExerciseTo expected = new ExerciseTo(uuid, "title", "description", 1);

        stubFor(WireMock.get("/api/exercises/" + uuid)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(asJson(expected))));
        ExerciseTo actual = this.endpoint.get(uuid);

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
