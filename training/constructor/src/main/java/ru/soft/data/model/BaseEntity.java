package ru.soft.data.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class BaseEntity implements Persistable<UUID>, HasId {

    @Id
    protected final UUID id;

    @Transient
    protected final boolean isNew;

    @Override
    public boolean isNew() {
        return this.isNew;
    }
}
