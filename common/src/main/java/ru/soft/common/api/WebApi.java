package ru.soft.common.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface WebApi<TO> {

    TO get(UUID id);

    List<TO> getAll();

    Page<TO> getPage(Pageable pageable);

    long count();

    void delete(UUID id);

    TO add(TO to);

    void update(TO to);
}
