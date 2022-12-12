package ru.soft.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.data.relational.core.mapping.Column;
import ru.soft.data.BaseEntity;
import ru.soft.data.model.snapshot.WorkoutSchemaSnapshot;

import java.util.UUID;

import static ru.soft.data.model.utils.ComplexityUtils.calculateComplexity;

@Getter
abstract class Workout extends BaseEntity {

    @NotNull
    @JsonProperty("workoutSchema")
    @Column("workout_schema")
    protected final WorkoutSchemaSnapshot workoutSchemaSnapshot;

    @Min(value = 1)
    @Max(value = 10)
    @Column("complexity")
    protected final int complexity;

    protected Workout(UUID id, boolean isNew, WorkoutSchemaSnapshot workoutSchemaSnapshot) {
        super(id, isNew);
        this.workoutSchemaSnapshot = workoutSchemaSnapshot;
        this.complexity = calculateComplexity(workoutSchemaSnapshot);
    }

    protected Workout(UUID id, WorkoutSchemaSnapshot workoutSchemaSnapshot, int complexity) {
        super(id, false);
        this.workoutSchemaSnapshot = workoutSchemaSnapshot;
        this.complexity = complexity;
    }
}
