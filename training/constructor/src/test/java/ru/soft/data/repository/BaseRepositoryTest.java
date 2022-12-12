package ru.soft.data.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.soft.TestContainerHolder;
import ru.soft.data.BaseEntity;
import ru.soft.web.exception.IllegalRequestDataException;
import ru.soft.web.utils.ValidationUtil;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class BaseRepositoryTest<T extends BaseEntity> extends TestContainerHolder {

    protected abstract BaseRepository<T> repository();

    protected abstract T forSave();

    protected abstract T expectedSaved(UUID id);

    @Test
    void getExisted() {
        List<T> exercises = repository().findAll();
        Assertions.assertFalse(exercises.isEmpty());
        T expected = exercises.get(0);
        T actual = repository().getExisted(expected.id());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getExistedNotExiting() {
        UUID notExitingId = UUID.randomUUID();
        IllegalRequestDataException actual = Assertions.assertThrows(
                IllegalRequestDataException.class, () -> repository().getExisted(notExitingId));
        Assertions.assertEquals(ValidationUtil.ENTITY_NOT_FOUND_TEMPLATE.formatted(notExitingId), actual.getMessage());
    }

    @Test
    void deleteById() {
        List<T> exercises = repository().findAll();
        Assertions.assertFalse(exercises.isEmpty());

        T deleted = exercises.get(0);
        UUID deletedId = deleted.getId();
        Assertions.assertNotNull(deletedId);

        repository().deleteExisted(deletedId);
        Optional<T> deletedOpt = repository().findById(deletedId);
        Assertions.assertTrue(deletedOpt.isEmpty());
    }

    @Test
    void deleteByIdNotExiting() {
        UUID notExitingId = UUID.randomUUID();
        IllegalRequestDataException actual = Assertions.assertThrows(
                IllegalRequestDataException.class, () -> repository().deleteExisted(notExitingId));
        Assertions.assertEquals(ValidationUtil.ENTITY_NOT_FOUND_TEMPLATE.formatted(notExitingId), actual.getMessage());
    }

    @Test
    void getAll() {
        List<T> exercises = repository().findAll();
        Assertions.assertEquals(3, exercises.size());
    }

    @Test
    void addNew() {
        T forSave = forSave();
        T saved = repository().save(forSave);
        Assertions.assertNotNull(saved.getId());

        Optional<T> workoutPlanOpt = repository().findById(saved.getId());
        Assertions.assertTrue(workoutPlanOpt.isPresent());

        T actual = workoutPlanOpt.get();
        T expected = expectedSaved(actual.id());
        Assertions.assertEquals(expected, actual);
    }
}
