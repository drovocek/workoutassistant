package ru.soft.common.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import ru.soft.common.data.HasId;

import java.util.UUID;

public record ExerciseTo(
        @JsonProperty("id") UUID id,
        @JsonProperty("title") @NotBlank String title,
        @JsonProperty("description") String description,
        @JsonProperty("complexity") @Range(min = 1, max = 10) int complexity) implements HasId {

    @Override
    public ExerciseTo withId(UUID id) {
        return new ExerciseTo(id, title(), description(), complexity());
    }
}
