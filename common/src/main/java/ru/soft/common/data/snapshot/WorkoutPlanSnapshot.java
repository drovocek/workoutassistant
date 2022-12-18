package ru.soft.common.data.snapshot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
@Getter
@ToString
@EqualsAndHashCode
@JsonRootName("workoutPlan")
@JsonIncludeProperties({"workoutSchema", "title", "description"})
public class WorkoutPlanSnapshot {
    @NotNull
    @JsonProperty("workoutSchema")
    private final WorkoutSchemaSnapshot workoutSchemaSnapshot;

    @NotBlank
    @JsonProperty("title")
    private final String title;

    @JsonProperty("description")
    private final String description;

    @Builder
    @JsonCreator
    public WorkoutPlanSnapshot(
            @JsonProperty("schema") WorkoutSchemaSnapshot workoutSchemaSnapshot,
            @JsonProperty("title") String title,
            @JsonProperty("description") String description) {
        this.workoutSchemaSnapshot = workoutSchemaSnapshot;
        this.title = title;
        this.description = description;
    }
}
