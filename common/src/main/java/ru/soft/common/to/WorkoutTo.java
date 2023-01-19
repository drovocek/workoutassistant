package ru.soft.common.to;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.soft.common.data.HasDescription;
import ru.soft.common.data.HasId;
import ru.soft.common.data.elements.WorkoutSchema;

import java.util.UUID;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@JsonRootName("workout")
@RequiredArgsConstructor
@JsonIncludeProperties({"id", "workoutSchema", "title", "note"})
public class WorkoutTo implements HasId, HasDescription {

    @JsonProperty("id")
    private final UUID id;

    @Valid
    @NotNull
    @Nonnull
    @JsonProperty("workoutSchema")
    private final WorkoutSchema workoutSchema;

    @NotBlank
    @JsonProperty("title")
    private final String title;

    @JsonProperty("note")
    private final String note;

    @Override
    public WorkoutTo withId(UUID id) {
        return WorkoutTo.builder()
                .id(id)
                .workoutSchema(this.workoutSchema())
                .title(this.title())
                .note(this.note())
                .build();
    }
}