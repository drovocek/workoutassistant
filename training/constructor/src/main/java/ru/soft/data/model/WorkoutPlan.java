package ru.soft.data.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.data.BaseEntity;
import ru.soft.common.data.snapshot.WorkoutSchemaSnapshot;

import java.util.UUID;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "workout_plan")
public class WorkoutPlan extends Workout {

    @Builder
    public WorkoutPlan(UUID id, boolean isNew, WorkoutSchemaSnapshot workoutSchemaSnapshot, String title, String description) {
        super(id, isNew, workoutSchemaSnapshot, title, description);
    }

    @PersistenceCreator
    public WorkoutPlan(UUID id, WorkoutSchemaSnapshot workoutSchemaSnapshot, String title, String description) {
        super(id, workoutSchemaSnapshot, title, description);
    }

    @Override
    protected BaseEntity withId(UUID id, boolean isNew) {
        return WorkoutPlan.builder()
                .id(id)
                .isNew(isNew)
                .workoutSchemaSnapshot(this.workoutSchemaSnapshot())
                .title(this.title())
                .description(this.description())
                .build();
    }
}
