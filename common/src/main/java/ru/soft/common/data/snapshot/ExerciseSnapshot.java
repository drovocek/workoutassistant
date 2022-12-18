package ru.soft.common.data.snapshot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
@JsonRootName("exercise")
public record ExerciseSnapshot(
        @JsonProperty("title") @NotBlank String title,
        @JsonProperty("description") @NotBlank String description,
        @JsonProperty("complexity") @Min(value = 1) @Max(value = 10) int complexity) {
}
