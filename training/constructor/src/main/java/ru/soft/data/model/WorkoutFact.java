package ru.soft.data.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.data.model.snapshot.WorkoutRoundSnapshot;

import java.util.List;
import java.util.UUID;

@Getter
@ToString(callSuper = true)
@Table(name = "workout_fact")
public class WorkoutFact extends Workout {

    @NotBlank
    @Column("note")
    private final String note;

    @Builder
    public WorkoutFact(UUID id, boolean isNew, List<WorkoutRoundSnapshot> workoutRoundSnapshots, String note) {
        super(id, isNew, workoutRoundSnapshots);
        this.note = note;
    }

    @PersistenceCreator
    public WorkoutFact(UUID id, List<WorkoutRoundSnapshot> workoutRoundSnapshots, String note) {
        super(id, workoutRoundSnapshots);
        this.note = note;
    }

    @Override
    public WorkoutFact newWithId(UUID id) {
        return WorkoutFact.builder()
                .id(id)
                .isNew(true)
                .workoutRoundSnapshots(this.workoutSchemaSnapshot().workoutRoundSnapshots())
                .note(this.note())
                .build();
    }
}
