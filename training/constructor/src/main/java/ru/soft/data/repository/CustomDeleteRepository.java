package ru.soft.data.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

import static ru.soft.utils.ValidationUtil.checkModification;

@NoRepositoryBean
public interface CustomDeleteRepository {

    int delete(@Param(value = "id") UUID id);

    default void deleteExisted(UUID id) {
        checkModification(delete(id), id);
    }
}
