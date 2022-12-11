package ru.soft.data.model.snapshot;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record WorkoutRoundSnapshot(@JsonProperty("stations") List<WorkoutStationSnapshot> workoutStations,
                                   String title, String description, int complexity) {
}
