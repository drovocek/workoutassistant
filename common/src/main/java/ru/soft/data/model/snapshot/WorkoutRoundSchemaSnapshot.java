package ru.soft.data.model.snapshot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

@JsonRootName("roundSchema")
public record WorkoutRoundSchemaSnapshot(
        @JsonProperty("roundStations") List<WorkoutStationSnapshot> workoutStationSnapshots) {
}
