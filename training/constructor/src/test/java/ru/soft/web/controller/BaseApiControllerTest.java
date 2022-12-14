package ru.soft.web.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.soft.TestContainerHolder;
import ru.soft.TestSettings;
import ru.soft.data.BaseEntity;
import ru.soft.data.HasId;
import ru.soft.data.repository.BaseRepository;
import ru.soft.utils.MatcherFactory;
import ru.soft.web.mapper.TOMapper;
import ru.soft.web.utils.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
abstract class BaseApiControllerTest<T extends BaseEntity, TO extends HasId> extends TestContainerHolder {

    @Autowired
    protected BaseRepository<T> repository;

    @Autowired
    protected TOMapper<T, TO> mapper;

    @Autowired
    protected MockMvc mockMvc;

    protected int rowsCount() {
        return TestSettings.DEFAULT_TEST_ROWS_COUNT;
    }

    protected abstract String getApiUrl();

    protected abstract MatcherFactory.Matcher<TO> matcher();

    protected abstract UUID expectedId();

    protected abstract TO requestEntity(boolean isNew);

    protected abstract List<TO> invalids(boolean isNew);

    protected abstract List<TO> duplicates(boolean isNew);

    protected abstract List<TO> htmlUnsafe(boolean isNew);

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    protected TO expected() {
        T existed = this.repository.getExisted(expectedId());
        return this.mapper.toTo(existed);
    }

    protected List<TO> expectedAll() {
        List<T> all = this.repository.findAll();
        Assertions.assertEquals(rowsCount(), all.size());

        return all.stream()
                .map(d -> this.mapper.toTo(d))
                .toList();
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
        perform(MockMvcRequestBuilders.get(getApiUrl()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(matcher().contentJson(expectedAll()));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(getApiUrl() + '/' + expectedId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(matcher().contentJson(expected()));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(getApiUrl() + '/' + UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void delete() {
        delete(expectedId(), status().isNoContent());
    }

    @Test
    void deleteNotFound() {
        delete(UUID.randomUUID(), status().isNotFound());
    }

    @Test
    void update() {
        TO forUpdate = requestEntity(false);
        update(forUpdate, status().isNoContent());

        TO actual = this.mapper.toTo(this.repository.getExisted(expectedId()));
        matcher().assertMatch(actual, forUpdate);
    }

    @Test
    void updateInvalids() {
        invalids(false).forEach(invalid ->
                update(invalid, status().isUnprocessableEntity()));
    }

    @Test
    void updateDuplicates() {
        duplicates(false).forEach(duplicate ->
                update(duplicate, status().isUnprocessableEntity()));
    }

    @Test
    void updateHtmlUnsafe() {
        htmlUnsafe(false).forEach(htmlUnsafe ->
                update(htmlUnsafe, status().isUnprocessableEntity()));
    }

    @Test
    void add() throws UnsupportedEncodingException {
        TO forAdd = requestEntity(true);
        ResultActions resultAction = add(forAdd, status().isCreated());

        TO to = matcher().readFromJson(resultAction);
        T actual = this.mapper.fromTo(to);
        T expectedWithoutId = this.mapper.fromTo(forAdd);

        Assertions.assertEquals(expectedWithoutId.withId(actual.id()), actual);
    }

    @Test
    void addInvalids() {
        invalids(true).forEach(invalid ->
                add(invalid, status().isUnprocessableEntity()));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void addDuplicates() {
        duplicates(true).forEach(duplicate ->
                add(duplicate, status().isUnprocessableEntity()));
    }

    @Test
    void addHtmlUnsafe() {
        htmlUnsafe(true).forEach(htmlUnsafe ->
                add(htmlUnsafe, status().isUnprocessableEntity()));
    }
}