package ru.soft.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Column;
import ru.soft.data.BaseEntity;
import ru.soft.data.model.snapshot.WorkoutSchemaSnapshot;

import java.util.UUID;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
abstract class Workout extends BaseEntity {

    @NotNull
    @JsonProperty("workoutSchema")
    @Column("workout_schema")
    protected final WorkoutSchemaSnapshot workoutSchemaSnapshot;

    @NotBlank
    @Column("title")
    protected final String title;

    @Column("description")
    protected final String description;

    protected Workout(UUID id, boolean isNew, WorkoutSchemaSnapshot workoutSchemaSnapshot, String title, String description) {
        super(id, isNew);
        this.workoutSchemaSnapshot = workoutSchemaSnapshot;
        this.title = title;
        this.description = description;
    }

    protected Workout(UUID id, WorkoutSchemaSnapshot workoutSchemaSnapshot, String title, String description) {
        super(id, false);
        this.workoutSchemaSnapshot = workoutSchemaSnapshot;
        this.title = title;
        this.description = description;
    }
}
