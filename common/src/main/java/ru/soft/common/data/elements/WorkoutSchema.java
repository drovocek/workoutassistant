package ru.soft.common.data.elements;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@JsonRootName("workoutSchema")
@JsonIncludeProperties({"workoutElements"})
public class WorkoutSchema {

    @Nonnull
    @NotNull
    @JsonProperty("workoutElements")
    private final List<WorkoutElement> workoutElements;

    @JsonCreator
    public WorkoutSchema(@JsonProperty("workoutElements") List<WorkoutElement> workoutElements) {
        this.workoutElements = workoutElements.stream()
                .sorted()
                .toList();
    }
}