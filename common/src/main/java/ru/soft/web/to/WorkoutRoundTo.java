package ru.soft.web.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.soft.data.HasId;
import ru.soft.data.model.snapshot.WorkoutRoundSchemaSnapshot;

import java.util.UUID;

public record WorkoutRoundTo(
        UUID id,
        @NotNull @JsonProperty("roundSchema") WorkoutRoundSchemaSnapshot workoutRoundSchemaSnapshot,
        @NotBlank String title,
        String description) implements HasId {
}