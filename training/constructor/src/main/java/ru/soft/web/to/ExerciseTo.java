package ru.soft.web.to;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import org.hibernate.validator.constraints.Range;
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

    @NotBlank
    String description;

    @NotNull
    @Range(min = 1, max = 10)
    Integer complexity;

    @JsonCreator
    public ExerciseTo(@JsonProperty("id") UUID id,
                      @JsonProperty("title") String title,
                      @JsonProperty("description") String description,
                      @JsonProperty("complexity") Integer complexity) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.complexity = complexity;
    }
}
