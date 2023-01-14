package ru.soft.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@JsonRootName("stationsSchema")
public record StationsSchema(
        @JsonProperty("stations") @Nonnull @NotNull List<Station> stations) {
}
