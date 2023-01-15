package ru.soft.common.data.snapshot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ru.soft.common.data.RoundsSchema;

import java.time.LocalDateTime;

@Builder
@JsonRootName("workoutSnapshot")
public record WorkoutSnapshot(
        @JsonProperty("roundsSchema") @NotNull @Nonnull RoundsSchema roundsSchema,
        @JsonProperty("title") String title,
        @JsonProperty("description") String description,
        @JsonProperty("dateTime") LocalDateTime dateTime) {
}
