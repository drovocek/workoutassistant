package ru.soft.common.data.elements;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

    private final UUID exerciseId;

    private final int repetitions;

    private final int weight;

    private final int duration;

    private final DurationUnit unit;

    private final int order;

    @Builder
    @JsonCreator
    public Station(
            @JsonProperty("title") @Nonnull @NotBlank String title,
            @JsonProperty("note") String note,
            @JsonProperty("exerciseId") UUID exerciseId,
            @JsonProperty("repetitions") @Min(value = 0) @Max(value = 1_000) int repetitions,
            @JsonProperty("weight") @Min(value = 0) @Max(value = 500) int weight,
            @JsonProperty("duration") @Min(value = 0) @Max(value = 60) int duration,
            @JsonProperty("unit") @Nonnull @NotNull DurationUnit unit,
            @JsonProperty("order") int order) {
        super(title, note);
        this.exerciseId = exerciseId;
        this.repetitions = repetitions;
        this.weight = weight;
        this.duration = duration;
        this.unit = Optional.ofNullable(unit).orElse(DurationUnit.SEC);
        this.order = order;
    }
}
