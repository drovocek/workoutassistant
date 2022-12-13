package ru.soft.web.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.soft.data.HasId;
import ru.soft.data.model.snapshot.WorkoutSchemaSnapshot;

import java.util.UUID;

public record WorkoutResultTo(
        UUID id,
        UUID workoutSessionId,
        @NotNull @JsonProperty("workoutSchema") WorkoutSchemaSnapshot workoutSchemaSnapshot,
        @NotBlank String title,
        String description) implements HasId {
}