package ru.soft.web.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import ru.soft.data.HasId;
import ru.soft.data.model.snapshot.WorkoutPlanSnapshot;

import java.time.LocalDateTime;
import java.util.UUID;

public record WorkoutSessionTo(
        UUID id,
        @NotNull @JsonProperty("workoutPlan") WorkoutPlanSnapshot plan,
        @NotNull LocalDateTime dateTime,
        String note) implements HasId {
}