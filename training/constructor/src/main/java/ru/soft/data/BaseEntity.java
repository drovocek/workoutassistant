package ru.soft.data;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;

import java.util.UUID;

@ToString
@EqualsAndHashCode
public abstract class BaseEntity implements Persistable<UUID>, HasId {

    @NotNull
    @Id
    @Column("id")
    protected final UUID id;

    @Transient
    @AccessType(AccessType.Type.PROPERTY)
    protected final boolean isNew;

    protected BaseEntity(UUID id, boolean isNew) {
        this.id = id;
        this.isNew = isNew;
    }

    public UUID id() {
        return this.id;
    }

    public UUID getId() {
        return id();
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    protected abstract BaseEntity withId(UUID id, boolean isNew);

    public BaseEntity newWithId(UUID id) {
        return withId(id, true);
    }

    public BaseEntity withId(UUID id) {
        return withId(id, false);
    }
}
