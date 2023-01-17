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

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@JsonRootName("trainingSession")
public class TrainingSessionTo implements HasId, HasDescription {

    @JsonProperty("id")
    private final UUID id;

    @Nonnull
    @NotNull
    @JsonProperty("schema")
    private final WorkoutSchema workoutSchema;

    @JsonProperty("dateTime")
    private final LocalDateTime dateTime;

    @NotBlank
    @JsonProperty("title")
    private final String title;

    @JsonProperty("note")
    private final String note;

    @Override
    public TrainingSessionTo withId(UUID id) {
        return TrainingSessionTo.builder()
                .id(id)
                .workoutSchema(this.workoutSchema())
                .dateTime(this.dateTime())
                .title(this.title())
                .note(this.note())
                .build();
    }
}