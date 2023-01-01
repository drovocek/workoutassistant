package ru.soft.common.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.soft.common.data.HasId;
import ru.soft.common.data.snapshot.WorkoutRoundSchemaSnapshot;

import java.util.UUID;

public record WorkoutRoundTo(
        @JsonProperty("id") UUID id,
        @JsonProperty("roundSchema") @Nonnull @NotNull WorkoutRoundSchemaSnapshot roundSchema,
        @JsonProperty("title") @NotBlank String title,
        @JsonProperty("description") String description) implements HasId {

    @Override
    public WorkoutRoundTo withId(UUID id) {
        return new WorkoutRoundTo(id, roundSchema(), title(), description());
    }
}