package ru.soft.data.model.snapshot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

@JsonRootName("workout_schema")
public record WorkoutSchemaSnapshot(
        @JsonProperty("rounds") List<WorkoutRoundSnapshot> workoutRoundSnapshots) {
}
