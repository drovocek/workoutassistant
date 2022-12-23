package ru.soft.testdata;

import java.util.List;
import java.util.UUID;

public interface TestDataStore<T> {

    T entity(boolean isNew);

    List<T> entities(boolean isNew);

    T requestEntity(boolean isNew);

    List<T> invalids(boolean isNew);

    List<T> duplicates(boolean isNew);

    List<T> htmlUnsafe(boolean isNew);

    default UUID newId(){
        return UUID.randomUUID();
    }
}
