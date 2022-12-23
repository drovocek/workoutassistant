package ru.soft.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
import ru.soft.service.BaseApiService;
import ru.soft.utils.MatcherFactory;
import ru.soft.web.exception.IllegalRequestDataException;
import ru.soft.web.utils.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.soft.utils.ValidationUtil.ENTITY_NOT_FOUND_TEMPLATE;

//https://www.baeldung.com/spring-boot-testing
@WebMvcTest
@AutoConfigureMockMvc
abstract class AbstractApiControllerTest<TO extends HasId> {

    @MockBean
    protected BaseApiService<TO> service;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper mapper;

    @BeforeEach
    void initData() {
        BDDMockito.given(this.service.getAll())
                .willReturn(toStore().tos());
        JsonUtil.setMapper(mapper);
    }

    protected int rowsCount() {
        return TestSettings.DEFAULT_TEST_ROWS_COUNT;
    }

    protected abstract String getApiUrl();

    protected abstract MatcherFactory.Matcher<TO> matcher();

    protected abstract TestToStore<TO> toStore();

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    protected List<TO> expectedAll() {
        List<TO> all = toStore().tos();
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
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(getApiUrl() + "/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(matcher().contentJson(expectedAll()));
    }

    @Test
    void get() throws Exception {
        UUID id = toStore().to().id();
        TO to = toStore().to();

        BDDMockito.given(this.service.get(id))
                .willReturn(to);

        perform(MockMvcRequestBuilders.get(getApiUrl() + '/' + id))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(matcher().contentJson(to));
    }

    @Test
    void getNotFound() throws Exception {
        UUID id = toStore().to().id();

        BDDMockito.willThrow(
                        new IllegalRequestDataException(
                                ENTITY_NOT_FOUND_TEMPLATE.formatted(id),
                                HttpStatus.NOT_FOUND))
                .given(this.service)
                .get(id);

        perform(MockMvcRequestBuilders.get(getApiUrl() + '/' + id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void delete() {
        UUID id = toStore().to().id();
        delete(id, status().isNoContent());
    }

    @Test
    void deleteNotFound() {
        UUID id = toStore().to().id();

        BDDMockito.willThrow(
                        new IllegalRequestDataException(
                                ENTITY_NOT_FOUND_TEMPLATE.formatted(id),
                                HttpStatus.NOT_FOUND))
                .given(this.service)
                .delete(id);

        delete(id, status().isNotFound());
    }

    @Test
    void update() {
        TO updated = toStore().requestTo(false);
        update(updated, status().isNoContent());
    }

    @Test
    void updateInvalids() {
        toStore().invalids(false).forEach(invalid ->
                update(invalid, status().isUnprocessableEntity()));
    }

    @Test
    void updateDuplicates() {
        toStore().duplicates(false).forEach(duplicate -> {
            BDDMockito.doThrow(DataIntegrityViolationException.class)
                    .when(this.service)
                    .update(duplicate);
            update(duplicate, status().isUnprocessableEntity());
        });
    }

    @Test
    void updateHtmlUnsafe() {
        toStore().htmlUnsafe(false).forEach(htmlUnsafe ->
                update(htmlUnsafe, status().isUnprocessableEntity()));
    }

    @Test
    void add() throws UnsupportedEncodingException {
        TO newEntity = toStore().requestTo(true);
        TO expected = toStore().requestTo(false);

        BDDMockito.given(this.service.add(newEntity))
                .willReturn(expected);

        ResultActions resultAction = add(newEntity, status().isCreated());
        TO actual = matcher().readFromJson(resultAction);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void addInvalids() {
        toStore().invalids(true)
                .forEach(invalid ->
                        add(invalid, status().isUnprocessableEntity()));
    }

    @Test
    void addDuplicates() {
        toStore().duplicates(true)
                .forEach(duplicate -> {
                            BDDMockito.doThrow(DataIntegrityViolationException.class)
                                    .when(this.service)
                                    .add(duplicate);
                            add(duplicate, status().isUnprocessableEntity());
                        }
                );
    }

    @Test
    void addHtmlUnsafe() {
        toStore().htmlUnsafe(true)
                .forEach(htmlUnsafe ->
                        add(htmlUnsafe, status().isUnprocessableEntity()));
    }
}