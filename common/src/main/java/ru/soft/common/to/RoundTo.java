package ru.soft.common.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ru.soft.common.data.HasId;
import ru.soft.common.data.StationsSchema;

import java.util.UUID;

@Builder
public record RoundTo(
        @JsonProperty("id") UUID id,
        @JsonProperty("stationsSchema") @NotNull @Nonnull StationsSchema stationsSchema,
        @JsonProperty("title") @NotBlank String title,
        @JsonProperty("description") String description) implements HasId {

    @Override
    public RoundTo withId(UUID id) {
        return new RoundTo(id, stationsSchema(), title(), description());
    }
}