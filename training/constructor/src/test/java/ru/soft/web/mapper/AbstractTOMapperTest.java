package ru.soft.web.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.soft.common.data.HasId;
import ru.soft.common.testdata.TestDataStore;
import ru.soft.data.BaseEntity;

import java.util.List;

abstract class AbstractTOMapperTest<T extends BaseEntity, TO extends HasId> {

    abstract TOMapper<T, TO> mapper();

    abstract TestDataStore<T> entityStore();

    abstract TestDataStore<TO> toStore();

    @Test
    void toTo() {
        List<T> entities = entityStore().entities(false);

        List<TO> actualTos = entities.stream()
                .map(e -> mapper().toTo(e))
                .toList();

        List<TO> expectedTos = toStore().entities(false);

        Assertions.assertEquals(expectedTos, actualTos);

        List<T> newEntities = entityStore().entities(true);

        List<TO> actualNewTos = newEntities.stream()
                .map(e -> mapper().toTo(e))
                .toList();

        List<TO> expectedNewTos = toStore().entities(true);

        Assertions.assertEquals(expectedNewTos, actualNewTos);
    }

    @Test
    void fromTo() {
        List<TO> tos = toStore().entities(false);

        List<T> actualEntities = tos.stream()
                .map(to -> mapper().fromTo(to))
                .toList();

        List<T> expectedEntities = entityStore().entities(false);

        Assertions.assertEquals(expectedEntities, actualEntities);

        List<TO> newTos = toStore().entities(true);

        List<T> actualNewEntities = newTos.stream()
                .map(to -> mapper().fromTo(to))
                .toList();

        List<T> expectedNewEntities = entityStore().entities(true);

        Assertions.assertEquals(expectedNewEntities, actualNewEntities);
    }
}