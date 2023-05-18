package ru.soft.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.soft.common.api.WebApi;
import ru.soft.common.data.HasId;

import java.util.List;
import java.util.UUID;

public interface BaseApiService<TO extends HasId>  extends WebApi<TO> {

    TO get(UUID id);

    List<TO> getAll();

    Page<TO> getPage(Pageable pageable);

    long count();

    void delete(UUID id);

    TO add(TO to);

    void update(TO to);
}
