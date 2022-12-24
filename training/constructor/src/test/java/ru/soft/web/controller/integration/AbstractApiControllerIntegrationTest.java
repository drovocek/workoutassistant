package ru.soft.web.controller.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import ru.soft.TestContainerHolder;
import ru.soft.TestSettings;
import ru.soft.common.data.HasId;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.common.utils.JsonUtil;
import ru.soft.data.BaseEntity;
import ru.soft.data.repository.BaseRepository;
import ru.soft.utils.MatcherFactory;

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
abstract class AbstractApiControllerIntegrationTest<T extends BaseEntity, TO extends HasId> extends TestContainerHolder {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected BaseRepository<T> repository;

    @BeforeEach
    protected void populateDB() {
        this.repository.saveAll(all(true));
    }

    protected int rowsCount() {
        return TestSettings.DEFAULT_TEST_ROWS_COUNT;
    }

    protected abstract String getApiUrl();

    protected abstract MatcherFactory.Matcher<TO> matcher();

    protected abstract TestDataStore<T> entityStore();

    protected abstract TestDataStore<TO> toStore();

    protected List<T> all(boolean isNew) {
        return entityStore().entities(isNew);
    }

    protected TO expected(boolean isNew) {
        return toStore().entity(isNew);
    }

    protected TO requestEntity(boolean isNew) {
        return toStore().requestEntity(isNew);
    }

    protected List<TO> invalids(boolean isNew) {
        return toStore().invalids(isNew);
    }

    protected List<TO> duplicates(boolean isNew) {
        return toStore().duplicates(isNew);
    }

    protected List<TO> htmlUnsafe(boolean isNew) {
        return toStore().htmlUnsafe(isNew);
    }

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }

    protected List<TO> expectedAll() {
        List<TO> all = toStore().entities(false);
        Assertions.assertEquals(rowsCount(), all.size());
        return all;
    }

    T getExited() {
        List<T> all = this.repository.findAll();
        return all.get(0);
    }

    UUID getExitedId() {
        List<T> all = this.repository.findAll();
        return all.get(0).id();
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
        perform(MockMvcRequestBuilders.get(getApiUrl() + "/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(matcher().contentJson(expectedAll()));
    }

    @Test
    @DisplayName("должен возвращать экземпляр сущности с запрашиваемым id")
    void get() throws Exception {
        UUID exitedId = getExitedId();
        TO expected = (TO) expected(false).withId(exitedId);

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

        perform(MockMvcRequestBuilders.get(getApiUrl() + '/' + notExitedId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("должен удалять экземпляр сущности с запрашиваемым id")
    void delete() {
        UUID exitedId = getExitedId();
        delete(exitedId, status().isNoContent());
    }

    @Test
    @DisplayName("должен выбрасывать ошибку, если удаляемый экземпляр сущности не найден")
    void deleteNotFound() {
        UUID notExitedId = UUID.randomUUID();
        delete(notExitedId, status().isNotFound());
    }

    @Test
    @DisplayName("должен обновлять существующий экземпляр сущности")
    void update() {
        TO updated = (TO) requestEntity(false).withId(getExitedId());
        update(updated, status().isNoContent());
    }

    @Test
    @DisplayName("не должен обновлять существующие экземпляры сущности, с полями не прошедшими валидацию на сервере")
    void updateInvalids() {
        invalids(false)
                .forEach(invalid -> update(invalid, status().isUnprocessableEntity()));
    }

    @Test
    @DisplayName("не должен обновлять существующие экземпляры сущности, с полями нарушающими ограничения базы данных")
    void updateDuplicates() {
        UUID exitedId = getExitedId();
        duplicates(false).stream()
                .map(to -> (TO) to.withId(exitedId))
                .forEach(duplicate -> update(duplicate, status().isUnprocessableEntity()));
    }

    @Test
    @DisplayName("не должен обновлять существующие экземпляры сущности, с полями нарушающими ограничения")
    void updateHtmlUnsafe() {
        htmlUnsafe(false)
                .forEach(htmlUnsafe -> update(htmlUnsafe, status().isUnprocessableEntity()));
    }

    @Test
    @DisplayName("должен добавлять новый экземпляр сущности")
    void add() throws UnsupportedEncodingException {
        TO newEntity = requestEntity(true);
        TO expected = requestEntity(false);

        ResultActions resultAction = add(newEntity, status().isCreated());
        TO actual = matcher().readFromJson(resultAction);

        matcher().assertMatch(expected, actual);
    }

    @Test
    @DisplayName("не должен добавлять новый экземпляр сущности, с полями не прошедшими валидацию на сервере")
    void addInvalids() {
        invalids(true)
                .forEach(invalid ->
                        add(invalid, status().isUnprocessableEntity()));
    }

    @Test
    @DisplayName("не должен добавлять новый экземпляр сущности, с полями нарушающими ограничения базы данных")
    void addDuplicates() {
        duplicates(true)
                .forEach(duplicate -> add(duplicate, status().isUnprocessableEntity()));
    }

    @Test
    @DisplayName("не должен добавлять новый экземпляр сущности, с полями нарушающими ограничения")
    void addHtmlUnsafe() {
        htmlUnsafe(true)
                .forEach(htmlUnsafe -> add(htmlUnsafe, status().isUnprocessableEntity()));
    }
}
