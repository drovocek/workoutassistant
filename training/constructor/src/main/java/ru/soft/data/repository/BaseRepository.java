package ru.soft.data.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static ru.soft.web.utils.ValidationUtil.checkExisted;
import static ru.soft.web.utils.ValidationUtil.checkModification;

@NoRepositoryBean
public interface BaseRepository<T> extends CrudRepository<T, UUID> {

    @Modifying
    @Transactional
    @Query("DELETE FROM #{#entityName} e WHERE e.id = :id")
    int delete(UUID id);

    default void deleteExisted(UUID id) {
        checkModification(delete(id), id);
    }

    @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id")
    T get(UUID id);

    default T getExisted(UUID id) {
        return checkExisted(get(id), id);
    }
}