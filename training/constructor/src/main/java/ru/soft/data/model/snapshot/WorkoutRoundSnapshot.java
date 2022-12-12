package ru.soft.data.model.snapshot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;

@Getter
@ToString
@EqualsAndHashCode
@JsonIncludeProperties({"schema", "title", "description"})
public class WorkoutRoundSnapshot {

    @JsonProperty("schema")
    private final WorkoutRoundSchemaSnapshot workoutRoundSchemaSnapshot;
    @JsonProperty("title")
    private final String title;
    @JsonProperty("description")
    private final String description;

    @Builder
    @JsonCreator
    @PersistenceCreator
    public WorkoutRoundSnapshot(@JsonProperty("schema") WorkoutRoundSchemaSnapshot workoutRoundSchemaSnapshot,
                                @JsonProperty("title") String title,
                                @JsonProperty("description") String description) {
        this.workoutRoundSchemaSnapshot = workoutRoundSchemaSnapshot;
        this.title = title;
        this.description = description;
    }
}
