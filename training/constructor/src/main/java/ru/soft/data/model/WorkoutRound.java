package ru.soft.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.soft.data.BaseEntity;
import ru.soft.data.BaseEntityWithComplexity;
import ru.soft.data.model.snapshot.WorkoutStationSnapshot;

import java.util.List;
import java.util.UUID;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Table(name = "workout_round")
public class WorkoutRound extends BaseEntityWithComplexity {

    @JsonProperty("stations")
    @Column("round_schema")
    private final List<WorkoutStationSnapshot> workoutStationSnapshots;

    @Column("title")
    private final String title;

    @Column("description")
    private final String description;

    @Min(value = 1)
    @Max(value = 10)
    @Column("complexity")
    private final int complexity;

    @Builder
    public WorkoutRound(UUID id, boolean isNew, List<WorkoutStationSnapshot> workoutStationSnapshots, String title, String description, int complexity) {
        super(id, isNew);
        this.workoutStationSnapshots = workoutStationSnapshots;
        this.title = title;
        this.description = description;
        this.complexity = complexity;
    }

    @PersistenceCreator
    public WorkoutRound(UUID id, List<WorkoutStationSnapshot> workoutStationSnapshots, String title, String description, int complexity) {
        super(id, false);
        this.workoutStationSnapshots = workoutStationSnapshots;
        this.title = title;
        this.description = description;
        this.complexity = complexity;
    }

    private int calculateComplexity(List<WorkoutStationSnapshot> workoutStationSnapshots) {
        return (int) workoutStationSnapshots.stream()
                .mapToInt(station -> station.exerciseSnapshot().complexity())
                .average().orElse(0);
    }

    @Override
    public BaseEntity newWithId(UUID id) {
        return WorkoutRound.builder()
                .id(id)
                .isNew(true)
                .workoutStationSnapshots(this.workoutStationSnapshots())
                .title(this.title())
                .description(this.description())
                .complexity(this.complexity())
                .build();
    }

    @Override
    public BaseEntity newWithActualComplexity(UUID id) {
        return WorkoutRound.builder()
                .id(id)
                .isNew(true)
                .workoutStationSnapshots(this.workoutStationSnapshots())
                .title(this.title())
                .description(this.description())
                .complexity(calculateComplexity(this.workoutStationSnapshots()))
                .build();
    }

    @Override
    public BaseEntity copyWithActualComplexity() {
        return newWithActualComplexity(id());
    }
}
