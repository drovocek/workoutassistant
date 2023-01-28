package ru.soft.common.data.elements;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@JsonRootName("workoutSchema")
@JsonIncludeProperties({"workoutElements"})
public class WorkoutSchema {

    @NotNull
    @Nonnull
    @JsonProperty("workoutElements")
    private final List<WorkoutElement> workoutElements;

    @JsonCreator
    public WorkoutSchema(List<WorkoutElement> workoutElements) {
        this.workoutElements = workoutElements.stream()
                .sorted()
                .toList();
    }
}