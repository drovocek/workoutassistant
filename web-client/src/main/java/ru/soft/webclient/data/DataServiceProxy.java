package ru.soft.webclient.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.soft.common.to.ExerciseTo;

import java.util.Optional;
import java.util.UUID;

public interface DataServiceProxy {
    Page<ExerciseTo> getPage(Pageable page);

    Optional<ExerciseTo> get(UUID id);

    ExerciseTo add(ExerciseTo entity);

    void update(ExerciseTo entity);

    void delete(UUID id);

    int count();
}
