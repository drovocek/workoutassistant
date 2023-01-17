package ru.soft.common.data.elements;

import com.fasterxml.jackson.annotation.JsonCreator;
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

    private final int duration;

    private final DurationUnit unit;

    private final int order;

    @Builder
    @JsonCreator
    public Rest(
            @JsonProperty("title") String title,
            @JsonProperty("note") String note,
            @JsonProperty("duration") int duration,
            @JsonProperty("unit") @Nonnull @NotNull DurationUnit unit,
            @JsonProperty("order") int order) {
        super(Optional.ofNullable(title).orElse("rest"), Optional.ofNullable(note).orElse("rest between sets"));
        this.duration = duration;
        this.unit = unit;
        this.order = order;
    }
}
