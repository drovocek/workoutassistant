package ru.soft.common.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.soft.common.data.HasId;
import ru.soft.common.data.snapshot.WorkoutSchemaSnapshot;

import java.util.UUID;

public record WorkoutPlanTo(
        @JsonProperty("id") UUID id,
        @JsonProperty("workoutSchema") @NotNull WorkoutSchemaSnapshot workoutSchemaSnapshot,
        @JsonProperty("title") @NotBlank String title,
        @JsonProperty("description") String description) implements HasId {
}