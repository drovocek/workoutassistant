package ru.soft.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ru.soft.common.data.snapshot.ExerciseSnapshot;

@Builder
@JsonRootName("station")
public record Station(@JsonProperty("exerciseSnapshot") @Nonnull @NotNull ExerciseSnapshot exerciseSnapshot,
                      @JsonProperty("repetitions") @Min(value = 0) @Max(value = 1_000) int repetitions,
                      @JsonProperty("weight") @Min(value = 0) @Max(value = 500) int weight,
                      @JsonProperty("duration") @Min(value = 0) @Max(value = 100_000) int duration,
                      @JsonProperty("rest") @Min(value = 0) @Max(value = 60) int rest,
                      @JsonProperty("order") @Min(value = 0) @Max(value = 100) int order) {
}
