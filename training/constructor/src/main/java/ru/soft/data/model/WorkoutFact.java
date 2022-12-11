package ru.soft.data.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Getter
@ToString(callSuper = true)
@Table(name = "workout_fact")
public class WorkoutFact extends Workout {

    @NotBlank
    @Column("note")
    private final String note;

    @PersistenceCreator
    public WorkoutFact(UUID id, List<WorkoutRound> workoutRounds, String note) {
        super(id, workoutRounds);
        this.note = note;
    }

    @Builder
    public WorkoutFact(UUID id, boolean isNew, List<WorkoutRound> workoutRounds, String note) {
        super(id, isNew, workoutRounds);
        this.note = note;
    }

    @Override
    public WorkoutFact newWithId(UUID id) {
        return WorkoutFact.builder()
                .id(id)
                .isNew(true)
                .workoutRounds(this.workoutSchemaSnapshot().workoutRounds())
                .note(this.note())
                .build();
    }
}
