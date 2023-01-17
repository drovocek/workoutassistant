package ru.soft.common.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import ru.soft.common.data.HasDescription;
import ru.soft.common.data.HasId;

import java.util.UUID;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@JsonRootName("exercise")
@RequiredArgsConstructor
public class ExerciseTo implements HasId, HasDescription {

    @JsonProperty("id")
    private final UUID id;

    @NotBlank
    @JsonProperty("title")
    private final String title;

    @JsonProperty("note")
    private final String note;

    @Override
    public ExerciseTo withId(UUID id) {
        return ExerciseTo.builder()
                .id(id)
                .title(this.title())
                .note(this.note())
                .build();
    }
}
