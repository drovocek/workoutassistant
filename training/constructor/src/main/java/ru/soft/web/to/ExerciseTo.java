package ru.soft.web.to;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import ru.soft.data.model.HasId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Value
@Builder
@ToString
public class ExerciseTo implements HasId {

    UUID id;
    @NotBlank
    String title;
    String description;
    @NotNull
    Integer complexity;
}
