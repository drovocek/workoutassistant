package ru.soft.data.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.soft.TestContainerHolder;
import ru.soft.TestSettings;
import ru.soft.data.BaseEntity;
import ru.soft.testdata.TestDataStore;
import ru.soft.web.exception.IllegalRequestDataException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ru.soft.utils.ValidationUtil.ENTITY_NOT_FOUND_TEMPLATE;

@DataJdbcTest
abstract class BaseRepositoryTest<T extends BaseEntity> extends TestContainerHolder {

    @Autowired
    protected BaseRepository<T> repository;

    @BeforeEach
    void initRepo() {
        this.repository.saveAll(entityStore().entities());
    }

    protected abstract TestDataStore<T> entityStore();

    protected int rowsCount() {
        return TestSettings.DEFAULT_TEST_ROWS_COUNT;
    }

    @Test
    void getAll() {
        List<T> exercises = this.repository.findAll();
        Assertions.assertEquals(rowsCount(), exercises.size());
    }

    @Test
    void get() {
        T expected = entityStore().entity();
        T actual = this.repository.getExisted(expected.id());
        Assertions.assertEquals(expected, actual);
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
        UUID id = entityStore().entity().id();
        this.repository.deleteExisted(id);
        Optional<T> deletedOpt = this.repository.findById(id);
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
        T updated = entityStore().requestEntity(false);
        this.repository.save(updated);
        T actual = this.repository.getExisted(updated.id());
        Assertions.assertEquals(updated, actual);
    }

    @Test
    void updateInvalids() {
        entityStore().invalids(false).forEach(invalid -> {
            DataIntegrityViolationException actual = Assertions.assertThrows(
                    DataIntegrityViolationException.class, () -> this.repository.save(invalid));
            Assertions.assertEquals(ENTITY_NOT_FOUND_TEMPLATE.formatted(invalid), actual.getMessage());
        });
    }

    @Test
    void updateDuplicates() {
        entityStore().duplicates(false).forEach(duplicate -> {
            DataIntegrityViolationException actual = Assertions.assertThrows(
                    DataIntegrityViolationException.class, () -> this.repository.save(duplicate));
            Assertions.assertEquals(ENTITY_NOT_FOUND_TEMPLATE.formatted(duplicate), actual.getMessage());
        });
    }

    @Test
    void save() {
        T forSave = entityStore().requestEntity(true);
        T saved = this.repository.save(forSave);
        Assertions.assertNotNull(saved.getId());

        Optional<T> savedOpt = this.repository.findById(saved.getId());
        Assertions.assertTrue(savedOpt.isPresent());

        T actual = savedOpt.get();

        Assertions.assertEquals(actual.withId(actual.id()), actual);
    }
}
