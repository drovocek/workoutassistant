package ru.soft.data.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

import static ru.soft.utils.ValidationUtil.ENTITY_NOT_FOUND_TEMPLATE;

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
    void getAll() {
        List<T> actual = this.repository.findAll();
        Assertions.assertEquals(rowsCount(), actual.size());

        List<T> expected = all(false);
        Assertions.assertEquals(rowsCount(), expected.size());

        matcher().assertMatch(actual, expected);
    }

    @Test
    void get() {
        T actual = this.repository.getExisted(getExitedId());
        matcher().assertMatch(actual, getExited());
    }

    @Test
    void getNotFound() {
        UUID notExitingId = UUID.randomUUID();

        IllegalRequestDataException actual = Assertions.assertThrows(
                IllegalRequestDataException.class, () -> this.repository.getExisted(notExitingId));

        Assertions.assertEquals(ENTITY_NOT_FOUND_TEMPLATE.formatted(notExitingId), actual.getMessage());
    }

    @Test
    void delete() {
        UUID exitedId = getExitedId();
        this.repository.deleteExisted(exitedId);
        Optional<T> deletedOpt = this.repository.findById(exitedId);

        Assertions.assertTrue(deletedOpt.isEmpty());
    }

    @Test
    void deleteNotFound() {
        UUID notExitingId = UUID.randomUUID();

        IllegalRequestDataException actual = Assertions.assertThrows(
                IllegalRequestDataException.class, () -> this.repository.deleteExisted(notExitingId));

        Assertions.assertEquals(ENTITY_NOT_FOUND_TEMPLATE.formatted(notExitingId), actual.getMessage());
    }

    @Test
    void update() {
        UUID exitedId = getExitedId();

        T updated = (T) requestEntity(false).withId(exitedId);

        this.repository.save(updated);
        T actual = this.repository.getExisted(updated.id());
        Assertions.assertEquals(updated, actual);
    }

    @Test
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
    void save() {
        T forSave = requestEntity(true);
        T saved = this.repository.save(forSave);
        Assertions.assertNotNull(saved.getId());

        Optional<T> savedOpt = this.repository.findById(saved.getId());
        Assertions.assertTrue(savedOpt.isPresent());

        T actual = savedOpt.get();

        Assertions.assertEquals(actual.withId(actual.id()), actual);
    }
}
