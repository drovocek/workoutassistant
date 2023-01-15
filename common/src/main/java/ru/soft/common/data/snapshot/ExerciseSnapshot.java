package ru.soft.common.data.snapshot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@JsonRootName("exerciseSnapshot")
public record ExerciseSnapshot(
        @JsonProperty("title") @NotBlank String title,
        @JsonProperty("description") @NotBlank String description) {
}
