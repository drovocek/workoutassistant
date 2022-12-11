package ru.soft.data;

import lombok.ToString;

import java.util.UUID;

@ToString
public abstract class BaseEntityWithComplexity extends BaseEntity {

    protected BaseEntityWithComplexity(UUID id, boolean isNew) {
        super(id, isNew);
    }

    public abstract BaseEntity newWithActualComplexity(UUID id);

    public abstract BaseEntity copyWithActualComplexity();
}
