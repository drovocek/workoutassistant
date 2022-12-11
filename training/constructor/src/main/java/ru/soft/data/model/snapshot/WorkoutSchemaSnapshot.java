package ru.soft.data.model.snapshot;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.soft.data.model.WorkoutRound;

import java.util.List;

public record WorkoutSchemaSnapshot(@JsonProperty("rounds") List<WorkoutRound> workoutRounds) {
}
