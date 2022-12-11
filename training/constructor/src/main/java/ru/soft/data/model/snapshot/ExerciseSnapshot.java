package ru.soft.data.model.snapshot;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@JsonRootName("Person")
public record ExerciseSnapshot(
        @NotBlank String title,
        @NotBlank String description,
        @Min(value = 1) @Max(value = 10) int complexity) {
}
