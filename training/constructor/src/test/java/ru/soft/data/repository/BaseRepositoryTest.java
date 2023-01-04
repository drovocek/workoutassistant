package ru.soft.data.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import ru.soft.TestContainerHolder;
import ru.soft.TestSettings;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.data.BaseEntity;
import ru.soft.utils.MatcherFactory;
import ru.soft.web.exception.IllegalRequestDataException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ru.soft.web.utils.ValidationUtil.ENTITY_NOT_FOUND_TEMPLATE;

@DataJdbcTest
abstract class BaseRepositoryTest<T extends BaseEntity> extends TestContainerHolder {

    @Autowired
    protected BaseRepository<T> repository;

    protected abstract TestDataStore<T> entityStore();

    protected abstract MatcherFactory.Matcher<T> matcher();

    protected int rowsCount() {
        return TestSettings.DEFAULT_TEST_ROWS_COUNT;
    }

    @BeforeEach
    protected void populateDB() {
        this.repository.saveAll(all(true));
    }

    protected List<T> all(boolean isNew){
        return entityStore().entities(isNew);
    }

    protected T requestEntity(boolean isNew){
        return entityStore().requestEntity(isNew);
    }

    protected List<T> invalids(boolean isNew){
        return entityStore().invalids(isNew);
    }

    protected List<T> duplicates(boolean isNew){
        return entityStore().duplicates(isNew);
    }

    protected List<T> htmlUnsafe(boolean isNew){
        return entityStore().htmlUnsafe(isNew);
    }

    T getExited() {
        List<T> all = this.repository.findAll();
        return all.get(0);
    }

    UUID getExitedId() {
        List<T> all = this.repository.findAll();
        return all.get(0).id();
    }

    @Test
    @DisplayName("должен возвращать все сохраненные экземпляры сущности")
    void getAll() {
        List<T> actual = this.repository.findAll();
        Assertions.assertEquals(rowsCount(), actual.size());

        List<T> expected = all(false);
        Assertions.assertEquals(rowsCount(), expected.size());

        matcher().assertMatch(actual, expected);
    }

    @Test
    @DisplayName("должен возвращать экземпляр сущности с запрашиваемым id")
    void get() {
        T actual = this.repository.getExisted(getExitedId());
        matcher().assertMatch(actual, getExited());
    }

    @Test
    @DisplayName("должен выбрасывать ошибку, если запрашиваемый экземпляр сущности не найден")
    void getNotFound() {
        UUID notExitingId = UUID.randomUUID();

        IllegalRequestDataException actual = Assertions.assertThrows(
                IllegalRequestDataException.class, () -> this.repository.getExisted(notExitingId));

        Assertions.assertEquals(ENTITY_NOT_FOUND_TEMPLATE.formatted(notExitingId), actual.getMessage());
    }

    @Test
    @DisplayName("должен удалять экземпляр сущности с запрашиваемым id")
    void delete() {
        UUID exitedId = getExitedId();
        this.repository.deleteExisted(exitedId);
        Optional<T> deletedOpt = this.repository.findById(exitedId);

        Assertions.assertTrue(deletedOpt.isEmpty());
    }

    @Test
    @DisplayName("должен выбрасывать ошибку, если удаляемый экземпляр сущности не найден")
    void deleteNotFound() {
        UUID notExitingId = UUID.randomUUID();

        IllegalRequestDataException actual = Assertions.assertThrows(
                IllegalRequestDataException.class, () -> this.repository.deleteExisted(notExitingId));

        Assertions.assertEquals(ENTITY_NOT_FOUND_TEMPLATE.formatted(notExitingId), actual.getMessage());
    }

    @Test
    @DisplayName("должен обновлять существующий экземпляр сущности")
    void update() {
        UUID exitedId = getExitedId();

        T updated = (T) requestEntity(false).withId(exitedId);

        this.repository.save(updated);
        T actual = this.repository.getExisted(updated.id());
        Assertions.assertEquals(updated, actual);
    }

    @Test
    @DisplayName("не должен обновлять существующие экземпляры сущности, с полями не прошедшими валидацию на сервере")
    void updateInvalids() {
        UUID exitedId = getExitedId();
        invalids(false).stream()
                .map(invalid -> (T) invalid.withId(exitedId))
                .forEach(invalid -> {
                    DbActionExecutionException actual = Assertions.assertThrows(
                            DbActionExecutionException.class, () -> this.repository.save(invalid));
                });
    }

    @Test
    @DisplayName("не должен обновлять существующие экземпляры сущности, с полями нарушающими ограничения базы данных")
    void updateDuplicates() {
        UUID exitedId = getExitedId();
        duplicates(false).stream()
                .map(duplicate -> (T) duplicate.withId(exitedId))
                .forEach(duplicate -> {
                    DbActionExecutionException actual = Assertions.assertThrows(
                            DbActionExecutionException.class, () -> this.repository.save(duplicate));
                });
    }

    @Test
    @DisplayName("не должен обновлять существующие экземпляры сущности, с полями нарушающими ограничения")
    void updateHtmlUnsafe() {
        UUID exitedId = getExitedId();
        htmlUnsafe(false).stream()
                .map(htmlUnsafe -> (T) htmlUnsafe.withId(exitedId))
                .forEach(htmlUnsafe -> {
                    DbActionExecutionException actual = Assertions.assertThrows(
                            DbActionExecutionException.class, () -> this.repository.save(htmlUnsafe));
                });
    }

    @Test
    @DisplayName("должен добавлять новый экземпляр сущности")
    void save() {
        T forSave = requestEntity(true);
        T saved = this.repository.save(forSave);
        Assertions.assertNotNull(saved.getId());

        Optional<T> savedOpt = this.repository.findById(saved.getId());
        Assertions.assertTrue(savedOpt.isPresent());

        T actual = savedOpt.get();

        Assertions.assertEquals(actual.withId(actual.id()), actual);
    }

    @Test
    @DisplayName("не должен добавлять новый экземпляр сущности, с полями не прошедшими валидацию на сервере")
    void saveInvalids() {
        UUID exitedId = getExitedId();
        invalids(true).stream()
                .map(invalid -> (T) invalid.withId(exitedId))
                .forEach(invalid -> {
                    DbActionExecutionException actual = Assertions.assertThrows(
                            DbActionExecutionException.class, () -> this.repository.save(invalid));
                });
    }

    @Test
    @DisplayName("не должен добавлять новый экземпляр сущности, с полями нарушающими ограничения базы данных")
    void saveDuplicates() {
        UUID exitedId = getExitedId();
        duplicates(true).stream()
                .map(duplicate -> (T) duplicate.withId(exitedId))
                .forEach(duplicate -> {
                    DbActionExecutionException actual = Assertions.assertThrows(
                            DbActionExecutionException.class, () -> this.repository.save(duplicate));
                });
    }

    @Test
    @DisplayName("не должен добавлять новый экземпляр сущности, с полями нарушающими ограничения")
    void saveHtmlUnsafe() {
        UUID exitedId = getExitedId();
        htmlUnsafe(true).stream()
                .map(htmlUnsafe -> (T) htmlUnsafe.withId(exitedId))
                .forEach(htmlUnsafe -> {
                    DbActionExecutionException actual = Assertions.assertThrows(
                            DbActionExecutionException.class, () -> this.repository.save(htmlUnsafe));
                });
    }
}
