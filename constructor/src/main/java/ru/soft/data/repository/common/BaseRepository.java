package ru.soft.data.repository.common;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ru.soft.utils.ValidationUtil.checkExisted;

@NoRepositoryBean
public interface BaseRepository<T> extends CustomDeleteRepository, CrudRepository<T, UUID>, PagingAndSortingRepository<T, UUID> {

    int delete(@Param(value = "id") UUID id);

    @Override
    List<T> findAll();

    default T getExisted(UUID id) {
        Optional<T> byId = findById(id);
        return checkExisted(byId.orElse(null), id);
    }
}
