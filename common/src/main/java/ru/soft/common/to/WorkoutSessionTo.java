package ru.soft.common.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ru.soft.common.data.HasId;
import ru.soft.common.data.snapshot.WorkoutSnapshot;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record WorkoutSessionTo(
        @JsonProperty("id") UUID id,
        @JsonProperty("workoutSnapshot") @NotNull @Nonnull WorkoutSnapshot workoutSnapshot,
        @JsonProperty("dateTime") @NotNull LocalDateTime dateTime,
        @JsonProperty("note") String note) implements HasId {

    @Override
    public WorkoutSessionTo withId(UUID id) {
        return new WorkoutSessionTo(id, workoutSnapshot(), dateTime(), note());
    }
}