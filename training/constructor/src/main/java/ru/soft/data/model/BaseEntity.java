package ru.soft.data.model;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import ru.soft.common.data.HasDescription;
import ru.soft.common.data.HasId;

import java.util.UUID;

@ToString
@EqualsAndHashCode
public abstract class BaseEntity implements HasId, HasDescription {

    @Id
    @NotNull
    @Column("id")
    protected final UUID id;

    @Column("title")
    protected final String title;

    @Column("note")
    protected final String note;

    @Transient
    @AccessType(AccessType.Type.PROPERTY)
    protected final boolean isNew;

    protected BaseEntity(UUID id, boolean isNew, String title, String note) {
        this.id = id;
        this.isNew = isNew;
        this.title = title;
        this.note = note;
    }

    public UUID id() {
        return this.id;
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    protected abstract BaseEntity withId(UUID id, boolean isNew);

    public BaseEntity withId(UUID id) {
        return withId(id, false);
    }

    public BaseEntity newWithId(UUID id) {
        return withId(id, true);
    }

    public @NotNull UUID getId() {
        return this.id;
    }

    public String title() {
        return this.title;
    }

    public String note() {
        return this.note;
    }
}
