package ru.soft.web.mapper;

import ru.soft.data.model.BaseEntity;
import ru.soft.common.data.HasId;

public interface TOMapper<T extends BaseEntity, TO extends HasId> {

    TO toTo(T entity);

    T fromTo(TO to);
}
