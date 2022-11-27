package ru.soft.data.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;

import java.util.UUID;

@Getter
@ToString
@RequiredArgsConstructor
public abstract class BaseEntity implements Persistable<UUID>, HasId {

    @Id
    @Column("id")
    protected final UUID id;

    @Transient
    @AccessType(AccessType.Type.PROPERTY)
    protected final boolean isNew;

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    public abstract BaseEntity newWithId(UUID id);
}
