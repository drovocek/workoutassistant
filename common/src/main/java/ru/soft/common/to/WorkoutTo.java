package ru.soft.common.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.soft.common.data.HasDescription;
import ru.soft.common.data.HasId;
import ru.soft.common.data.elements.WorkoutSchema;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@JsonRootName("workout")
@RequiredArgsConstructor
public class WorkoutTo implements HasId, HasDescription {

    @JsonProperty("id")
    private final UUID id;

    @NotNull
    @Nonnull
    @JsonProperty("schema")
    private final WorkoutSchema workoutSchema;

    @NotBlank
    @JsonProperty("title")
    private final String title;

    @JsonProperty("note")
    private final String note;

    @NotNull
    @Nonnull
    @JsonProperty("order")
    private final int order;

    @Override
    public WorkoutTo withId(UUID id) {
        return WorkoutTo.builder()
                .id(id)
                .workoutSchema(this.workoutSchema())
                .title(this.title())
                .note(this.note())
                .order(this.order())
                .build();
    }
}