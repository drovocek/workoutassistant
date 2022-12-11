package ru.soft.data.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.data.relational.core.mapping.Column;
import ru.soft.data.BaseEntity;
import ru.soft.data.model.snapshot.WorkoutSchemaSnapshot;

import java.util.List;
import java.util.UUID;

@Getter
abstract class Workout extends BaseEntity {

    @NotNull
    @Column("workout_schema")
    protected final WorkoutSchemaSnapshot workoutSchemaSnapshot;

    protected Workout(UUID id, List<WorkoutRound> workoutRounds) {
        super(id, false);
        this.workoutSchemaSnapshot = new WorkoutSchemaSnapshot(workoutRounds);
    }

    protected Workout(UUID id, boolean isNew, List<WorkoutRound> workoutRounds) {
        super(id, isNew);
        this.workoutSchemaSnapshot = new WorkoutSchemaSnapshot(workoutRounds);
    }
}
