package ru.soft.data.model.snapshot;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record WorkoutRoundSchemaSnapshot(@JsonProperty("stations") List<WorkoutStationSnapshot> workoutStations) {

    public WorkoutRoundSchemaSnapshot empty() {
        return new WorkoutRoundSchemaSnapshot(List.of());
    }
}
