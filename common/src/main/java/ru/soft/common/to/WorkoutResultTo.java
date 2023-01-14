package ru.soft.common.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.soft.common.data.HasId;
import ru.soft.common.data.snapshot.WorkoutSchemaSnapshot;

import java.util.UUID;

public record WorkoutResultTo(
        @JsonProperty("id") UUID id,
        @JsonProperty("workoutSessionId") @NotNull UUID workoutSessionId,
        @JsonProperty("workoutSchema") @NotNull @Nonnull WorkoutSchemaSnapshot workoutSchemaSnapshot,
        @JsonProperty("title") @NotBlank String title,
        @JsonProperty("description") String description) implements HasId {

    @Override
    public WorkoutResultTo withId(UUID id) {
        return new WorkoutResultTo(id, workoutSessionId(), workoutSchemaSnapshot(), title(), description());
    }
}