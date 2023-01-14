package ru.soft.common.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ru.soft.common.data.HasId;
import ru.soft.common.data.snapshot.WorkoutPlanSnapshot;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record WorkoutSessionTo(
        @JsonProperty("id") UUID id,
        @JsonProperty("workoutPlanSnapshot") @NotNull @Nonnull  WorkoutPlanSnapshot workoutPlanSnapshot,
        @JsonProperty("dateTime") @NotNull LocalDateTime dateTime,
        @JsonProperty("note") String note) implements HasId {

    @Override
    public WorkoutSessionTo withId(UUID id) {
        return new WorkoutSessionTo(id, workoutPlanSnapshot(), dateTime(), note());
    }
}