package ru.soft.data.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.data.BaseEntity;
import ru.soft.common.data.snapshot.WorkoutSchemaSnapshot;

import java.util.UUID;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "workout_result")
public class WorkoutResult extends Workout {

    @Column("workout_session_id")
    private final UUID workoutSessionId;

    @Builder
    public WorkoutResult(UUID id, UUID workoutSessionId, boolean isNew, WorkoutSchemaSnapshot workoutSchemaSnapshot, String title, String description) {
        super(id, isNew, workoutSchemaSnapshot, title, description);
        this.workoutSessionId = workoutSessionId;
    }

    @PersistenceCreator
    public WorkoutResult(UUID id, UUID workoutSessionId, WorkoutSchemaSnapshot workoutSchemaSnapshot, String title, String description) {
        super(id, workoutSchemaSnapshot, title, description);
        this.workoutSessionId = workoutSessionId;
    }

    @Override
    protected BaseEntity withId(UUID id, boolean isNew) {
        return WorkoutResult.builder()
                .id(id)
                .isNew(isNew)
                .workoutSessionId(this.workoutSessionId())
                .workoutSchemaSnapshot(this.workoutSchemaSnapshot())
                .title(this.title())
                .description(this.description())
                .build();
    }
}
