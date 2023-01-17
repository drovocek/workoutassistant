package ru.soft.data.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.common.data.elements.WorkoutSchema;

import java.util.UUID;

@Getter
@Table(name = "workout")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIncludeProperties({"id", "workoutSchema", "title", "note"})
public class Workout extends BaseEntity {

    @Column("workout_schema")
    @JsonProperty("workoutSchema")
    private final WorkoutSchema workoutSchema;

    @Builder
    public Workout(UUID id, boolean isNew, @NotNull WorkoutSchema workoutSchema, @NotBlank String title, String note) {
        super(id, isNew, title, note);
        this.workoutSchema = workoutSchema;
    }

    @PersistenceCreator
    public Workout(UUID id, WorkoutSchema workoutSchema, @NotBlank String title, String note) {
        super(id, false, title, note);
        this.workoutSchema = workoutSchema;
    }

    @Override
    protected Workout withId(UUID id, boolean isNew) {
        return Workout.builder()
                .id(id)
                .isNew(isNew)
                .workoutSchema(this.workoutSchema())
                .title(this.title())
                .note(this.note())
                .build();
    }
}
