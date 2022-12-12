package ru.soft.data.model.snapshot;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record WorkoutSchemaSnapshot(
        @JsonProperty("rounds") List<WorkoutRoundSnapshot> workoutRoundSnapshots) {
}
