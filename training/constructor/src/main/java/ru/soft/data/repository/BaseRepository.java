package ru.soft.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

import static ru.soft.web.utils.ValidationUtil.checkExisted;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends CrudRepository<T, ID> {

    @Override
    List<T> findAll();

    default T getExisted(ID id) {
        Optional<T> byId = findById(id);
        return checkExisted(byId.orElse(null), id);
    }
}
