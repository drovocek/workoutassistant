package ru.soft.data.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.soft.TestContainerHolder;
import ru.soft.TestSettings;
import ru.soft.data.BaseEntity;
import ru.soft.utils.ValidationUtil;
import ru.soft.web.exception.IllegalRequestDataException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public abstract class BaseRepositoryTest<T extends BaseEntity> extends TestContainerHolder {

    @Autowired
    protected BaseRepository<T> repository;

    protected abstract T forSave();

    protected abstract T expectedSaved(UUID id);

    protected int rowsCount() {
        return TestSettings.DEFAULT_TEST_ROWS_COUNT;
    }

    @Test
    void getExisted() {
        List<T> exercises = this.repository.findAll();
        Assertions.assertFalse(exercises.isEmpty());
        T expected = exercises.get(0);
        T actual = this.repository.getExisted(expected.id());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getExistedNotExiting() {
        UUID notExitingId = UUID.randomUUID();
        IllegalRequestDataException actual = Assertions.assertThrows(
                IllegalRequestDataException.class, () -> this.repository.getExisted(notExitingId));
        Assertions.assertEquals(ValidationUtil.ENTITY_NOT_FOUND_TEMPLATE.formatted(notExitingId), actual.getMessage());
    }

    @Test
    void deleteById() {
        List<T> exercises = this.repository.findAll();
        Assertions.assertFalse(exercises.isEmpty());

        T deleted = exercises.get(0);
        UUID deletedId = deleted.getId();
        Assertions.assertNotNull(deletedId);

        this.repository.deleteExisted(deletedId);
        Optional<T> deletedOpt = this.repository.findById(deletedId);
        Assertions.assertTrue(deletedOpt.isEmpty());
    }

    @Test
    void deleteByIdNotExiting() {
        UUID notExitingId = UUID.randomUUID();
        IllegalRequestDataException actual = Assertions.assertThrows(
                IllegalRequestDataException.class, () -> this.repository.deleteExisted(notExitingId));
        Assertions.assertEquals(ValidationUtil.ENTITY_NOT_FOUND_TEMPLATE.formatted(notExitingId), actual.getMessage());
    }

    @Test
    void getAll() {
        List<T> exercises = this.repository.findAll();
        Assertions.assertEquals(rowsCount(), exercises.size());
    }

    @Test
    void save() {
        save(forSave(), this::expectedSaved);
    }

    protected void save(T forSave, Function<UUID, T> expected) {
        T saved = this.repository.save(forSave);
        Assertions.assertNotNull(saved.getId());

        Optional<T> workoutPlanOpt = this.repository.findById(saved.getId());
        Assertions.assertTrue(workoutPlanOpt.isPresent());

        T actual = workoutPlanOpt.get();
        Assertions.assertEquals(expected.apply(actual.id()), actual);
    }
}
