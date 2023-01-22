package ru.soft.common.data.elements;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@Getter
@JsonRootName("rest")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIncludeProperties({"duration", "unit", "title", "note", "order"})
public class Rest extends WorkoutElement {

    @JsonProperty("duration")
    private final int duration;

    @Nonnull
    @NotNull
    @JsonProperty("unit")
    private final DurationUnit unit;

    @Builder
    public Rest(String title, String note, int duration, DurationUnit unit, int order) {
        super(Optional.ofNullable(title).orElse("rest"),
                Optional.ofNullable(note).orElse("rest between sets"),
                order);
        this.duration = duration;
        this.unit = unit;
    }
}
