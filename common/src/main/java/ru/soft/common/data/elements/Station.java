package ru.soft.common.data.elements;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.soft.common.to.ExerciseTo;

import java.util.Optional;

@Getter
@JsonRootName("station")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIncludeProperties(
        {"exercise", "repetitions", "weight", "duration", "unit", "title", "note", "order"})
public class Station extends WorkoutElement {

    @Nonnull
    @NotNull
    @JsonProperty("exercise")
    private final ExerciseTo exercise;

    @JsonProperty("repetitions")
    private final int repetitions;

    @Min(value = 0)
    @Max(value = 500)
    @JsonProperty("weight")
    private final int weight;

    @Min(value = 0)
    @Max(value = 500)
    @JsonProperty("duration")
    private final int duration;

    @Nonnull
    @NotNull
    @JsonProperty("unit")
    private final DurationUnit unit;

    @Builder
    public Station(@NotNull @NonNull ExerciseTo exercise, int repetitions, int weight, int duration, DurationUnit unit, int order) {
        super(exercise.title(), exercise.note(), order);
        this.exercise = exercise;
        this.repetitions = repetitions;
        this.weight = weight;
        this.duration = duration;
        this.unit = Optional.ofNullable(unit).orElse(DurationUnit.SEC);
    }
}
