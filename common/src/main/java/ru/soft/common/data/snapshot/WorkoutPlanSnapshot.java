package ru.soft.common.data.snapshot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ru.soft.common.data.RoundsSchema;

@Builder
@JsonRootName("workoutPlanSnapshot")
public record WorkoutPlanSnapshot(
        @JsonProperty("roundsSchema") @NotNull @Nonnull RoundsSchema roundsSchema,
        @JsonProperty("title") String title,
        @JsonProperty("description") String description) {
}
