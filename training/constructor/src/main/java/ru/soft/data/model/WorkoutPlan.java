package ru.soft.data.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.data.model.snapshot.WorkoutSchemaSnapshot;

import java.util.UUID;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "workout_plan")
public class WorkoutPlan extends Workout {

    @NotBlank
    @Column("title")
    private final String title;

    @NotBlank
    @Column("description")
    private final String description;

    @Builder
    public WorkoutPlan(UUID id, boolean isNew, WorkoutSchemaSnapshot workoutSchemaSnapshot, String title, String description) {
        super(id, isNew, workoutSchemaSnapshot);
        this.title = title;
        this.description = description;
    }

    @PersistenceCreator
    public WorkoutPlan(UUID id, WorkoutSchemaSnapshot workoutSchemaSnapshot, String title, String description, int complexity) {
        super(id, workoutSchemaSnapshot, complexity);
        this.title = title;
        this.description = description;
    }

    @Override
    public WorkoutPlan newWithId(UUID id) {
        return WorkoutPlan.builder()
                .id(id)
                .isNew(true)
                .workoutSchemaSnapshot(this.workoutSchemaSnapshot())
                .title(this.title())
                .description(this.description())
                .build();
    }
}
