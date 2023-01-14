package ru.soft.common.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ru.soft.common.data.HasId;
import ru.soft.common.data.RoundsSchema;

import java.util.UUID;

@Builder
public record WorkoutPlanTo(
        @JsonProperty("id") UUID id,
        @JsonProperty("roundsSchema") @NotNull @Nonnull RoundsSchema roundsSchema,
        @JsonProperty("title") @NotBlank String title,
        @JsonProperty("description") String description) implements HasId {

    @Override
    public WorkoutPlanTo withId(UUID id) {
        return new WorkoutPlanTo(id, roundsSchema(), title(), description());
    }
}