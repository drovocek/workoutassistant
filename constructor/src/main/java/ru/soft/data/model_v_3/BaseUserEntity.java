package ru.soft.data.model_v_3;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Column;

import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public abstract class BaseUserEntity extends BaseEntity {

    @NotNull
    @Column("id")
    protected final UUID userId;

    protected BaseUserEntity(UUID userId, UUID id, String name, String note, boolean isNew) {
        super(id, name, note, isNew);
        this.userId = userId;
    }
}
