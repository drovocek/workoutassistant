package ru.soft.web.mapper;

import ru.soft.data.BaseEntity;
import ru.soft.data.HasId;

public interface TOMapper<T extends BaseEntity, TO extends HasId> {

    TO toTo(T entity);

    T fromTo(TO to);
}
