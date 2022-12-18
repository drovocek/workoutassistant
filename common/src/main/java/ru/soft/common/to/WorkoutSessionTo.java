package ru.soft.common.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import ru.soft.common.data.HasId;
import ru.soft.common.data.snapshot.WorkoutPlanSnapshot;

import java.time.LocalDateTime;
import java.util.UUID;

public record WorkoutSessionTo(
        @JsonProperty("id") UUID id,
        @JsonProperty("workoutPlan") @NotNull WorkoutPlanSnapshot plan,
        @JsonProperty("dateTime") @NotNull LocalDateTime dateTime,
        @JsonProperty("note") String note) implements HasId {
}