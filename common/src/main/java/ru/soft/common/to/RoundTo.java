package ru.soft.common.to;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.soft.common.data.HasId;
import ru.soft.common.data.elements.WorkoutElement;
import ru.soft.common.data.elements.WorkoutSchema;

import java.util.UUID;

@Getter
@JsonRootName("round")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIncludeProperties({"id", "workoutSchema", "title", "note", "order"})
public class RoundTo extends WorkoutElement implements HasId {

    @JsonProperty("id")
    private final UUID id;

    @Valid
    @NotNull
    @Nonnull
    @JsonProperty("workoutSchema")
    private final WorkoutSchema workoutSchema;

    @Builder
    public RoundTo(@NotBlank String title, String note, int order, UUID id, WorkoutSchema workoutSchema) {
        super(title, note, order);
        this.id = id;
        this.workoutSchema = workoutSchema;
    }

    @Override
    public RoundTo withId(UUID id) {
        return RoundTo.builder()
                .id(id)
                .workoutSchema(this.workoutSchema())
                .title(this.title())
                .note(this.note())
                .build();
    }
}