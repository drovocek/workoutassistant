package ru.soft.common.data.elements;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;
import java.util.UUID;

@Getter
@JsonRootName("station")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIncludeProperties(
        {"exerciseId", "repetitions", "weight", "duration", "unit", "title", "note", "order"})
public class Station extends WorkoutElement {

    @Nonnull
    @NotNull
    @JsonProperty("exerciseId")
    private final UUID exerciseId;

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

    @JsonProperty("order")
    private final int order;

    @Builder
    public Station(String title, String note, UUID exerciseId, int repetitions, int weight, int duration, DurationUnit unit, int order) {
        super(title, note);
        this.exerciseId = exerciseId;
        this.repetitions = repetitions;
        this.weight = weight;
        this.duration = duration;
        this.unit = Optional.ofNullable(unit).orElse(DurationUnit.SEC);
        this.order = order;
    }
}
