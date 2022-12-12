package ru.soft.data.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.data.model.snapshot.WorkoutSchemaSnapshot;

import java.util.UUID;

@Getter
@ToString(callSuper = true)
@Table(name = "workout_fact")
public class WorkoutFact extends Workout {

    @NotBlank
    @Column("note")
    private final String note;

    @Builder
    public WorkoutFact(UUID id, boolean isNew, WorkoutSchemaSnapshot workoutSchemaSnapshot, String note) {
        super(id, isNew, workoutSchemaSnapshot);
        this.note = note;
    }

    @PersistenceCreator
    public WorkoutFact(UUID id, WorkoutSchemaSnapshot workoutSchemaSnapshot, String note, int complexity) {
        super(id, workoutSchemaSnapshot, complexity);
        this.note = note;
    }

    @Override
    public WorkoutFact newWithId(UUID id) {
        return WorkoutFact.builder()
                .id(id)
                .isNew(true)
                .workoutSchemaSnapshot(this.workoutSchemaSnapshot())
                .note(this.note())
                .build();
    }
}
