package ru.soft.data.model.snapshot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

@JsonRootName("round_schema")
public record WorkoutRoundSchemaSnapshot(
        @JsonProperty("stations") List<WorkoutStationSnapshot> workoutStationSnapshots) {
}
