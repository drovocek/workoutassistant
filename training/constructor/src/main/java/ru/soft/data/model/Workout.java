package ru.soft.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.data.relational.core.mapping.Column;
import ru.soft.data.BaseEntity;
import ru.soft.data.model.snapshot.WorkoutRoundSnapshot;
import ru.soft.data.model.snapshot.WorkoutSchemaSnapshot;

import java.util.List;
import java.util.UUID;

@Getter
abstract class Workout extends BaseEntity {

    @NotNull
    @JsonProperty("workoutSchema")
    @Column("workout_schema")
    protected final WorkoutSchemaSnapshot workoutSchemaSnapshot;

    protected Workout(UUID id, List<WorkoutRoundSnapshot> workoutRoundSnapshots) {
        super(id, false);
        this.workoutSchemaSnapshot = new WorkoutSchemaSnapshot(workoutRoundSnapshots);
    }

    protected Workout(UUID id, boolean isNew, List<WorkoutRoundSnapshot> workoutRoundSnapshots) {
        super(id, isNew);
        this.workoutSchemaSnapshot = new WorkoutSchemaSnapshot(workoutRoundSnapshots);
    }
}
