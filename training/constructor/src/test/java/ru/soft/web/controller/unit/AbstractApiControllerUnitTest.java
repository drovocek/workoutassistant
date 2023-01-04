package ru.soft.web.controller.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.soft.TestSettings;
import ru.soft.common.data.HasId;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.utils.JsonUtil;
import ru.soft.service.BaseApiService;
import ru.soft.utils.MatcherFactory;
import ru.soft.web.exception.IllegalRequestDataException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.soft.web.utils.ValidationUtil.ENTITY_NOT_FOUND_TEMPLATE;

//https://www.baeldung.com/spring-boot-testing
@WebMvcTest
@AutoConfigureMockMvc
abstract class AbstractApiControllerUnitTest<TO extends HasId> {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper mapper;

    @MockBean
    protected BaseApiService<TO> service;

    @BeforeEach
    void initData() {
        JsonUtil.setMapper(mapper);
    }

    protected int rowsCount() {
        return TestSettings.DEFAULT_TEST_ROWS_COUNT;
    }

    protected abstract String getApiUrl();

    protected abstract MatcherFactory.Matcher<TO> matcher();

    protected abstract TestDataStore<TO> toStore();

    protected BaseApiService<TO> service() {
        return this.service;
    }

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    protected List<TO> expectedAll() {
        List<TO> all = toStore().entities(false);
        Assertions.assertEquals(rowsCount(), all.size());
        return all;
    }

    protected void delete(UUID id, ResultMatcher matcher) {
        try {
            perform(MockMvcRequestBuilders.delete(getApiUrl() + '/' + id))
                    .andDo(print())
                    .andExpect(matcher);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void update(TO updated, ResultMatcher matcher) {
        try {
            perform(put(getApiUrl())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(updated)))
                    .andDo(print())
                    .andExpect(matcher);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected ResultActions add(TO added, ResultMatcher matcher) {
        try {
            return perform(post(getApiUrl())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(added)))
                    .andDo(print())
                    .andExpect(matcher);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//TODO When add security
//    @Test
//    void getUnauthorized() throws Exception {
//        perform(MockMvcRequestBuilders.get(getApiUrl()))
//                .andExpect(status().isUnauthorized());
//    }

//    @Test
//    void getForbidden() throws Exception {
//        perform(MockMvcRequestBuilders.get(getApiUrl()), user)
//                .andExpect(status().isForbidden());
//    }

    @Test
    @DisplayName("должен возвращать все сохраненные экземпляры сущности")
    void getAll() throws Exception {
        given(this.service.getAll())
                .willReturn(toStore().entities(false));

        perform(MockMvcRequestBuilders.get(getApiUrl() + "/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(matcher().contentJson(expectedAll()));
    }

    @Test
    @DisplayName("должен возвращать экземпляр сущности с запрашиваемым id")
    void get() throws Exception {
        TO exited = toStore().entity(false);
        UUID exitedId = exited.id();

        given(service().get(exitedId))
                .willReturn(exited);

        TO expected = toStore().entity(false);

        perform(MockMvcRequestBuilders.get(getApiUrl() + '/' + exitedId))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(matcher().contentJson(expected));
    }

    @Test
    @DisplayName("должен выбрасывать ошибку, если запрашиваемый экземпляр сущности не найден")
    void getNotFound() throws Exception {
        UUID notExitedId = UUID.randomUUID();

        BDDMockito.willThrow(
                        new IllegalRequestDataException(
                                ENTITY_NOT_FOUND_TEMPLATE.formatted(notExitedId),
                                HttpStatus.NOT_FOUND))
                .given(service())
                .get(notExitedId);

        perform(MockMvcRequestBuilders.get(getApiUrl() + '/' + notExitedId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("должен удалять экземпляр сущности с запрашиваемым id")
    void delete() {
        UUID id = toStore().entity(false).id();
        delete(id, status().isNoContent());
    }

    @Test
    @DisplayName("должен выбрасывать ошибку, если удаляемый экземпляр сущности не найден")
    void deleteNotFound() {
        UUID notExitedId = UUID.randomUUID();

        BDDMockito.willThrow(
                        new IllegalRequestDataException(
                                ENTITY_NOT_FOUND_TEMPLATE.formatted(notExitedId),
                                HttpStatus.NOT_FOUND))
                .given(service())
                .delete(notExitedId);

        delete(notExitedId, status().isNotFound());
    }

    @Test
    @DisplayName("должен обновлять существующий экземпляр сущности")
    void update() {
        TO updated = toStore().requestEntity(false);
        update(updated, status().isNoContent());
    }

    @Test
    @DisplayName("не должен обновлять существующие экземпляры сущности, с полями не прошедшими валидацию на сервере")
    void updateInvalids() {
        toStore().invalids(false)
                .forEach(invalid -> update(invalid, status().isUnprocessableEntity()));
    }

    @Test
    @DisplayName("не должен обновлять существующие экземпляры сущности, с полями нарушающими ограничения базы данных")
    void updateDuplicates() {
        toStore().duplicates(false)
                .forEach(duplicate -> {
                    doThrow(DataIntegrityViolationException.class)
                            .when(service())
                            .update(duplicate);

                    update(duplicate, status().isUnprocessableEntity());
                });
    }

    @Test
    @DisplayName("не должен обновлять существующие экземпляры сущности, с полями нарушающими ограничения")
    void updateHtmlUnsafe() {
        toStore().htmlUnsafe(false)
                .forEach(htmlUnsafe -> update(htmlUnsafe, status().isUnprocessableEntity()));
    }

    @Test
    @DisplayName("должен добавлять новый экземпляр сущности")
    void add() throws UnsupportedEncodingException {
        TO newEntity = toStore().requestEntity(true);
        TO expected = toStore().requestEntity(false);


        given(service().add(newEntity))
                .willReturn(expected);


        ResultActions resultAction = add(newEntity, status().isCreated());
        TO actual = matcher().readFromJson(resultAction);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("не должен добавлять новый экземпляр сущности, с полями не прошедшими валидацию на сервере")
    void addInvalids() {
        toStore().invalids(true)
                .forEach(invalid ->
                        add(invalid, status().isUnprocessableEntity()));
    }

    @Test
    @DisplayName("не должен добавлять новый экземпляр сущности, с полями нарушающими ограничения базы данных")
    void addDuplicates() {
        toStore().duplicates(true)
                .forEach(duplicate -> {
                            doThrow(DataIntegrityViolationException.class)
                                    .when(service())
                                    .add(duplicate);

                            add(duplicate, status().isUnprocessableEntity());
                        }
                );
    }

    @Test
    @DisplayName("не должен добавлять новый экземпляр сущности, с полями нарушающими ограничения")
    void addHtmlUnsafe() {
        toStore().htmlUnsafe(true)
                .forEach(htmlUnsafe ->
                        add(htmlUnsafe, status().isUnprocessableEntity()));
    }
}