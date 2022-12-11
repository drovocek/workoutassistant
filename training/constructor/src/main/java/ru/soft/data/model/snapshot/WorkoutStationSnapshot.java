package ru.soft.data.model.snapshot;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record WorkoutStationSnapshot(@JsonProperty("exercise") @NotNull ExerciseSnapshot exerciseSnapshot,
                                     @Min(value = 0) @Max(value = 1_000) int repetitions,
                                     @Min(value = 0) @Max(value = 500) int weight,
                                     @Min(value = 0) @Max(value = 100_000) int duration,
                                     @Min(value = 0) @Max(value = 60) int rest,
                                     @Min(value = 0) @Max(value = 100) int order) {
}
