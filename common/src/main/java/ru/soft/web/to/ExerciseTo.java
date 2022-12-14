package ru.soft.web.to;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import ru.soft.data.HasId;

import java.util.UUID;

public record ExerciseTo(
        UUID id,
        @NotBlank String title,
        String description,
        @Range(min = 1, max = 10) int complexity) implements HasId {
}
