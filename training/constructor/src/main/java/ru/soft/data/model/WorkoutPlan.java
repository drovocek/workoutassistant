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
@Table(name = "workout_plan")
public class WorkoutPlan extends Workout {

    @NotBlank
    @Column("title")
    private final String title;

    @NotBlank
    @Column("description")
    private final String description;

    @Builder
    public WorkoutPlan(UUID id, boolean isNew, List<WorkoutRoundSnapshot> workoutRoundSnapshots, String title, String description) {
        super(id, isNew, workoutRoundSnapshots);
        this.title = title;
        this.description = description;
    }

    @PersistenceCreator
    public WorkoutPlan(UUID id, List<WorkoutRoundSnapshot> workoutRoundSnapshots, String title, String description) {
        super(id, workoutRoundSnapshots);
        this.title = title;
        this.description = description;
    }

    @Override
    public WorkoutPlan newWithId(UUID id) {
        return WorkoutPlan.builder()
                .id(id)
                .isNew(true)
                .workoutRoundSnapshots(this.workoutSchemaSnapshot().workoutRoundSnapshots())
                .title(this.title())
                .description(this.description())
                .build();
    }
}
