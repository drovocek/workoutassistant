package ru.soft.common.data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public interface HasId {

    UUID id();

    HasId withId(UUID id);

    @JsonIgnore
    default boolean isNew() {
        return id() == null;
    }
}
