package ru.soft.data.model_v_3;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import ru.soft.common.data.HasDescription;
import ru.soft.common.data.HasId;

import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
public abstract class BaseEntity implements HasId, HasDescription {

    @Id
    @NotNull
    @Column("id")
    protected final UUID id;

    @Column("name")
    protected final String name;

    @Column("note")
    protected final String note;

    @Transient
    @AccessType(AccessType.Type.PROPERTY)
    protected final boolean isNew;

    protected BaseEntity(UUID id, String name, String note, boolean isNew) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.isNew = isNew;
    }

    public UUID id() {
        return this.id;
    }

    protected abstract HasId withId(UUID id, boolean isNew);

    public HasId withId(UUID id) {
        return withId(id, false);
    }

    public HasId newWithId(UUID id) {
        return withId(id, true);
    }
}
