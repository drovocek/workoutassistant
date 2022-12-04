package ru.soft.data.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.data.BaseEntity;

import java.util.Set;
import java.util.UUID;

@Getter
@ToString(callSuper = true)
@Table(name = "workout")
public class Workout extends BaseEntity {

    @NotNull
    @MappedCollection(idColumn = "workout_round_id")
    private final Set<WorkoutRound> workoutRounds;

    @NotBlank
    @Column("title")
    private final String title;

    @NotBlank
    @Column("description")
    private final String description;

    @PersistenceCreator
    public Workout(UUID id, Set<WorkoutRound> workoutRounds, String title, String description) {
        super(id, false);
        this.workoutRounds = workoutRounds;
        this.title = title;
        this.description = description;
    }

    @Builder
    public Workout(UUID id, boolean isNew, Set<WorkoutRound> workoutRounds, String title, String description) {
        super(id, isNew);
        this.workoutRounds = workoutRounds;
        this.title = title;
        this.description = description;
    }

    @Override
    public BaseEntity newWithId(UUID id) {
        return Workout.builder()
                .id(id)
                .isNew(true)
                .title(this.title())
                .description(this.description())
                .workoutRounds(this.workoutRounds())
                .build();
    }
}
